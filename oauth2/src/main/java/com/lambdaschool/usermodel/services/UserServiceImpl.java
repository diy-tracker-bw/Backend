package com.lambdaschool.usermodel.services;

import com.lambdaschool.usermodel.exceptions.ResourceFoundException;
import com.lambdaschool.usermodel.exceptions.ResourceNotFoundException;
import com.lambdaschool.usermodel.logging.Loggable;
import com.lambdaschool.usermodel.models.*;
import com.lambdaschool.usermodel.repository.RoleRepository;
import com.lambdaschool.usermodel.repository.UserRepository;
import com.lambdaschool.usermodel.view.UserNameCountEmails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Loggable
@Transactional
@Service(value = "userService")
public class UserServiceImpl implements UserService
{

    @Autowired
    UserAuditing userAuditing;
    @Autowired
    private UserRepository userrepos;
    @Autowired
    private RoleRepository rolerepos;

    public User findUserById(long id) throws ResourceNotFoundException
    {
        return userrepos.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("User id " + id + " not found!"));
    }

    @Override
    public List<User> findByNameContaining(String username,
                                           Pageable pageable)
    {
        return userrepos.findByUsernameContainingIgnoreCase(username.toLowerCase(),
                                                            pageable);
    }

    @Override
    public List<User> findAll(Pageable pageable)
    {
        List<User> list = new ArrayList<>();
        userrepos.findAll(pageable)
                 .iterator()
                 .forEachRemaining(list::add);
        return list;
    }

    @Transactional
    @Override
    public void delete(long id)
    {
        userrepos.findById(id)
                 .orElseThrow(() -> new ResourceNotFoundException("User id " + id + " not found!"));
        userrepos.deleteById(id);
    }

    @Override
    public User findByName(String name)
    {
        User uu = userrepos.findByUsername(name.toLowerCase());
        if (uu == null)
        {
            throw new ResourceNotFoundException("User name " + name + " not found!");
        }
        return uu;
    }

    @Transactional
    @Override
    public User save(User user)
    {
        if (userrepos.findByUsername(user.getUsername()
                                         .toLowerCase()) != null)
        {
            throw new ResourceFoundException(user.getUsername() + " is already taken!");
        }

        User newUser = new User();
        newUser.setUsername(user.getUsername()
                                .toLowerCase());
        newUser.setPasswordNotEncrypt(user.getPassword());
        newUser.setEmail(user.getEmail().toLowerCase());
        if(user.getPhotourl() != null)
            newUser.setPhotourl(user.getPhotourl());

        ArrayList<UserRoles> newRoles = new ArrayList<>();
        for (UserRoles ur : user.getUserroles())
        {
            long id = ur.getRole()
                        .getRoleid();
            Role role = rolerepos.findById(id)
                                 .orElseThrow(() -> new ResourceNotFoundException("Role id " + id + " not found!"));
            newRoles.add(new UserRoles(newUser,
                                       role));
        }
        newUser.setUserroles(newRoles);

//        for (Useremail ue : user.getUseremails())
//        {
//            newUser.getUseremails()
//                   .add(new Useremail(newUser,
//                                      ue.getUseremail()));
//        }

        for(Project currentProject: user.getProjects()){
            newUser.getProjects().add(new Project(currentProject.getProjectName(), currentProject.getInstructions(), currentProject.getPhotoUrl(), currentProject.getLikes(), newUser));
        }

        return userrepos.save(newUser);
    }

    @Transactional
    @Override
    public User update(User user,
                       long id,
                       boolean isAdmin)
    {
        Authentication authentication = SecurityContextHolder.getContext()
                                                             .getAuthentication();

        User authenticatedUser = userrepos.findByUsername(authentication.getName());

        if (id == authenticatedUser.getUserid() || isAdmin)
        {
            User currentUser = findUserById(id);

            if (user.getUsername() != null)
            {
                currentUser.setUsername(user.getUsername()
                                            .toLowerCase());
            }

            if (user.getPassword() != null)
            {
                currentUser.setPasswordNotEncrypt(user.getPassword());
            }

            if (user.getEmail() != null)
            {
                currentUser.setEmail(user.getEmail()
                                                .toLowerCase());
            }

            if(user.getPhotourl() != null)
            {
                currentUser.setPhotourl(user.getPhotourl());
            }

            if (user.getUserroles()
                    .size() > 0)
            {
                throw new ResourceFoundException("Couldn't Work");
            }

//            if (user.getUseremails()
//                    .size() > 0)
//            {
//                for (Useremail ue : user.getUseremails())
//                {
//                    currentUser.getUseremails()
//                               .add(new Useremail(currentUser,
//                                                  ue.getUseremail()));
//                }
//            }

            return userrepos.save(currentUser);
        } else
        {
            throw new ResourceNotFoundException(id + " Not current user");
        }
    }

    @Transactional
    @Override
    public void deleteUserRole(long userid,
                               long roleid)
    {
        userrepos.findById(userid)
                 .orElseThrow(() -> new ResourceNotFoundException("User id " + userid + " not found!"));
        rolerepos.findById(roleid)
                 .orElseThrow(() -> new ResourceNotFoundException("Role id " + roleid + " not found!"));

        if (rolerepos.checkUserRolesCombo(userid,
                                          roleid)
                     .getCount() > 0)
        {
            rolerepos.deleteUserRoles(userid,
                                      roleid);
        } else
        {
            throw new ResourceNotFoundException("Role and User Combination Does Not Exists");
        }
    }

    @Transactional
    @Override
    public void addUserRole(long userid,
                            long roleid)
    {
        userrepos.findById(userid)
                 .orElseThrow(() -> new ResourceNotFoundException("User id " + userid + " not found!"));
        rolerepos.findById(roleid)
                 .orElseThrow(() -> new ResourceNotFoundException("Role id " + roleid + " not found!"));

        if (rolerepos.checkUserRolesCombo(userid,
                                          roleid)
                     .getCount() <= 0)
        {
            rolerepos.insertUserRoles(userAuditing.getCurrentAuditor()
                                                  .get(),
                                      userid,
                                      roleid);
        } else
        {
            throw new ResourceFoundException("Role and User Combination Already Exists");
        }
    }

    @Override
    public List<UserNameCountEmails> getCountUserEmails()
    {
        return userrepos.getCountUserEmails();
    }
}
