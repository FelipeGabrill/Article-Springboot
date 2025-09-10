package com.topavnbanco.artigos.servicies;

import java.util.List;
import java.util.Set;

import com.topavnbanco.artigos.dto.ArticleDTO;
import com.topavnbanco.artigos.dto.RoleDTO;
import com.topavnbanco.artigos.dto.user.*;
import com.topavnbanco.artigos.entities.Role;
import com.topavnbanco.artigos.entities.User;
import com.topavnbanco.artigos.projections.UserDetailsProjection;
import com.topavnbanco.artigos.repositories.ArticleRepository;
import com.topavnbanco.artigos.repositories.CongressoRepository;
import com.topavnbanco.artigos.repositories.RoleRepository;
import com.topavnbanco.artigos.repositories.UserRepository;
import com.topavnbanco.artigos.servicies.exceptions.DatabaseException;
import com.topavnbanco.artigos.servicies.exceptions.ResourceNotFoundException;
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

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService implements UserDetailsService {

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
    private EmailService emailService;

    @Autowired
    private CardService cardService;

    @Autowired
    private AddressService addressService;

    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        User user = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado"));
        return new UserDTO(user);
    }

    @Transactional(readOnly = true)
    public UserArticlesDTO findUserWithArticles(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        List<ArticleDTO> articles = user.getUserArticles().stream()
                .map(ArticleDTO::new)
                .toList();

        return new UserArticlesDTO(user.getId(), user.getLogin(), articles);
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
        entity = repository.save(entity);
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        sendWelcomeEmail(entity.getLogin(), entity.getUsernameUser(), entity.getRoles());
        return new UserDTO(entity);
    }

    private void sendWelcomeEmail(String login, String name, Set<Role> roles) {

        String rolesStr = roles.stream()
                .map(r -> r.getAuthority().replace("ROLE_", "").toLowerCase())
                .reduce((a, b) -> a + ", " + b)
                .orElse("participant");

        String subject = "Bem-vindo ao TopAvn Banco Artigos!";
        String body = "Olá " + name + ",\n\n" +
                "Seja muito bem-vindo à nossa plataforma! 🎉\n" +
                "Agora você pode submeter seus artigos, participar de congressos e interagir com outros pesquisadores.\n\n" +
                "Estamos felizes em tê-lo conosco!\n\n" +
                "Equipe TopAvn Banco Artigos.\n\n" +
                "O seu papel atual no sistema é: " + rolesStr + ".";

        emailService.sendEmail(login, subject, body);
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
        entity.setIsReviewer(dto.getIsReviewer());
        entity.setWorkPlace(dto.getWorkPlace());
        entity.setCongresso(
                congressoRepository.findById(dto.getCongressoId())
                        .orElseThrow(() -> new ResourceNotFoundException("Congresso não encontrado: " + dto.getCongressoId()))
        );

        if (dto.getProfileImage() != null) {
            entity.setProfileImage(dto.getProfileImage());
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
        entity.setIsReviewer(dto.getIsReviewer());
        entity.setWorkPlace(dto.getWorkPlace());
        entity.setCongresso(
                congressoRepository.findById(dto.getCongressoId())
                        .orElseThrow(() -> new ResourceNotFoundException("Congresso não encontrado: " + dto.getCongressoId()))
        );


        // dto.getProfileImage() como byte[]
        if (dto.getProfileImage() != null) {
            entity.setProfileImage(dto.getProfileImage());
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
