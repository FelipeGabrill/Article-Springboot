package com.topavnbanco.artigos.application.servicies;

import com.topavnbanco.artigos.adapters.inbound.dtos.article.ArticleUserDTO;
import com.topavnbanco.artigos.adapters.inbound.dtos.user.*;
import com.topavnbanco.artigos.application.servicies.exceptions.DatabaseException;
import com.topavnbanco.artigos.application.servicies.exceptions.ResourceNotFoundException;
import com.topavnbanco.artigos.application.usecases.UserUseCases;
import com.topavnbanco.artigos.domain.article.Article;
import com.topavnbanco.artigos.domain.article.projections.ArticleSummaryProjection;
import com.topavnbanco.artigos.domain.article.repository.ArticleRepository;
import com.topavnbanco.artigos.domain.congresso.repository.CongressoRepository;
import com.topavnbanco.artigos.domain.role.Role;
import com.topavnbanco.artigos.adapters.inbound.dtos.role.RoleDTO;
import com.topavnbanco.artigos.domain.role.repository.RoleRepository;
import com.topavnbanco.artigos.domain.user.*;
import com.topavnbanco.artigos.domain.user.projections.UserDetailsProjection;
import com.topavnbanco.artigos.domain.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.List;

@Service
public class UserServiceImpl implements UserDetailsService, UserUseCases {

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository repository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CongressoRepository congressoRepository;

    @Autowired
    private EmailServiceImpl emailService;

    @Autowired
    private CardServiceImpl cardService;

    @Autowired
    private AddressServiceImpl addressService;

    @Transactional(readOnly = true)
    public UserSimpleDTO findById(Long id) {
        User user = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado"));
        return new UserSimpleDTO(user);
    }

    @Transactional(readOnly = true)
    public UserArticleDTO findByIdWithArticles(Long id, Pageable pageable) {
        User user = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado"));

        Page<ArticleSummaryProjection> articlesPage = articleRepository.findByArticlesUsers_Id(user.getId(), pageable);

        List<ArticleUserDTO> articleDTOs = articlesPage.getContent().stream()
                .map(a -> new ArticleUserDTO(a.getId(), a.getTitle(), a.getDescription(), a.getFormat()))
                .toList();


        return new UserArticleDTO(user, articleDTOs);
    }

    @Transactional(readOnly = true)
    public Page<UserSimpleDTO> findAllByCongressoId(Long congressoId, Pageable pageable) {
        Page<User> result = repository.findAllByCongressoId(congressoId, pageable);
        return result.map(x -> new UserSimpleDTO(x));
    }

    @Transactional(readOnly = true)
    public Page<UserSimpleDTO> findAll(Pageable pageable) {
        Page<User> result = repository.findAll(pageable);
        return result.map(x -> new UserSimpleDTO(x));
    }

    @Transactional
    public UserDTO insert(UserInsertDTO dto) {
        User entity = new User();
        copyDtoToEntityInsert(dto, entity);
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        entity = repository.save(entity);
        //emailService.sendWelcomeEmail(entity.getLogin(), entity.getUsernameUser(), entity.getRoles());
        return new UserDTO(entity);
    }

    @Transactional
    public UserSimpleDTO update(Long id, UserUpdateDTO dto) {
        try {
            User entity = repository.getReferenceById(id);
            copyDtoToEntityUpdate(dto, entity);
            entity = repository.save(entity);
            return new UserSimpleDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade referencial");
        }
    }

    private void copyDtoToEntityUpdate(UserUpdateDTO dto, User entity) {
        entity.setUsernameUser(dto.getUsernameUser());
        entity.setLogin(dto.getLogin());
        entity.setReviewer(dto.getIsReviewer());
        entity.setWorkPlace(dto.getWorkPlace());
        entity.setCongresso(
                congressoRepository.findById(dto.getCongressoId())
                        .orElseThrow(() -> new ResourceNotFoundException("Congresso não encontrado: " + dto.getCongressoId()))
        );

        byte[] img = decodeImageBase64(dto.getProfileImage());
        if (img != null && img.length > 0) {
            entity.setProfileImage(img);
        }

        for (RoleDTO roleDto : dto.getRoles()) {
            boolean exists = entity.getRoles().stream().anyMatch(role -> role.getId().equals(roleDto.getId()));
            if (!exists) {
                Role role = roleRepository.getReferenceById(roleDto.getId());
                entity.getRoles().add(role);
            }
        }

    }

    private void copyDtoToEntityInsert(UserDTO dto, User entity) {

        entity.setCard(cardService.insert(dto.getCardDTO()));
        entity.setAddress(addressService.insert(dto.getAddressDTO()));

        entity.setUsernameUser(dto.getUsernameUser());
        entity.setLogin(dto.getLogin());
        entity.setReviewer(dto.getIsReviewer());
        entity.setWorkPlace(dto.getWorkPlace());
        entity.setCongresso(
                congressoRepository.findById(dto.getCongressoId())
                        .orElseThrow(() -> new ResourceNotFoundException("Congresso não encontrado: " + dto.getCongressoId()))
        );


        // dto.getProfileImage() como byte[]
        byte[] img = decodeImageBase64(dto.getProfileImage());
        if (img != null && img.length > 0) {
            entity.setProfileImage(img);
        }
//        // dto.getProfileImageBase64() como String Base64
//        if (dto.getProfileImageBase64() != null && !dto.getProfileImageBase64().isBlank()) {
//            entity.setProfileImage(Base64.getDecoder().decode(dto.getProfileImageBase64()));
//        }

        for (RoleDTO roleDto : dto.getRoles()) {
            boolean exists = entity.getRoles().stream().anyMatch(role -> role.getId().equals(roleDto.getId()));
            if (!exists) {
                Role role = roleRepository.getReferenceById(roleDto.getId());
                entity.getRoles().add(role);
            }
        }

    }

    private static byte[] decodeImageBase64(String base64) {
        if (base64 == null || base64.isBlank()) {
            return null;
        }
        int comma = base64.indexOf(',');
        String payload = (comma >= 0) ? base64.substring(comma + 1) : base64;
        try {
            return Base64.getDecoder().decode(payload);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Imagem de perfil em Base64 inválida.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserDetailsProjection> result = repository.searchUserAndRolesByLogin(username);
        if (result.size() == 0) {
            throw new UsernameNotFoundException("User not found");
        }

        User user = new User();
        user.setLogin(username);
        user.setPassword(result.get(0).getPassword());
        for (UserDetailsProjection projection : result) {
            user.addRole(new Role(projection.getRoleId(), projection.getAuthority()));
        }

        return user;
    }

    @Transactional(readOnly = true)
    public UserDTO getMe() {
        User user = authenticated();
        return new UserDTO(user);
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> getByLogin(String login, Pageable pageable) {
        Page<User> result = repository.findByLoginIgnoreCaseContaining(login, pageable);
        return result.map(UserDTO::new);
    }

    protected User authenticated() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            Jwt jwtPrincipal = (Jwt) authentication.getPrincipal();
            String username = jwtPrincipal.getClaim("username");

            return repository.findByLogin(username).get();
        } catch (Exception e) {
            throw new UsernameNotFoundException("User not found");
        }
    }
}