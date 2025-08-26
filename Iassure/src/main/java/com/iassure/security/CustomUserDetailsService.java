package com.iassure.security;

import com.iassure.dto.UserDetailsDTO;
import com.iassure.exception.ResourceNotFoundException;
import com.iassure.user.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Naveen Kumar Chintala
 */
@Service
@Log4j2
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private final  PasswordEncoder passwordEncoder;

   public CustomUserDetailsService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    UserService userService;

   //@Autowired
   //OrganizationService organizationService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String mobileNoOrEmail)
            throws UsernameNotFoundException {
        // Let people login with either username or email
        com.iassure.entity.UserDetails user = userService.findByEmail(mobileNoOrEmail);

        UserDetailsDTO userDetailsDTO = new UserDetailsDTO();

        //OrganizationDetails organizationDetails = organizationService.retrieveOrgDetailsById(user.getOrganizationId());

        BeanUtils.copyProperties(user, userDetailsDTO);
        userDetailsDTO.setPassword(passwordEncoder.encode(userDetailsDTO.getPassword()));
        //userDetailsDTO.setOrganizationDetails(organizationDetails);

        return UserPrincipal.create(userDetailsDTO);
    }

    @Transactional
    public UserDetails loadUserById(Integer id) {
        com.iassure.entity.UserDetails user = userService.findById(id);
        if (user == null) {
            throw new ResourceNotFoundException("User", "id", id);
        }
        UserDetailsDTO userDetailsDTO = new UserDetailsDTO();

        BeanUtils.copyProperties(user, userDetailsDTO);
        userDetailsDTO.setPassword(passwordEncoder.encode(userDetailsDTO.getPassword()));

        log.info("User details are : {}", user);

        return UserPrincipal.create(userDetailsDTO);
    }
}
