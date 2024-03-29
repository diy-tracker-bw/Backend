package com.lambdaschool.usermodel.controllers;

import com.lambdaschool.usermodel.logging.Loggable;
import com.lambdaschool.usermodel.models.LoginUser;
import com.lambdaschool.usermodel.models.User;
import com.lambdaschool.usermodel.models.NewUser;
import com.lambdaschool.usermodel.models.UserRoles;
import com.lambdaschool.usermodel.services.RoleService;
import com.lambdaschool.usermodel.services.UserService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Loggable
@RestController
@Api(tags = {"OpenEndpoints"})
public class OpenController
{
    private static final Logger logger = LoggerFactory.getLogger(OpenController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    // Create the user and Return the access token
    // http://localhost:2019/createnewuser
    // Just create the user
    // http://localhost:2019/createnewuser?access=false
    //
    // {
    //     "username" : "Mojo",
    //     "password" : "corgie",
    //     "primaryemail" : "home@local.house"
    // }

    private String getPort(HttpServletRequest httpServletRequest)
    {
        if (httpServletRequest.getServerName()
                              .equalsIgnoreCase("localhost"))
        {
            return ":" + httpServletRequest.getLocalPort();
        } else
        {
            return "";
        }
    }

    /*@PostMapping(value = "/login",
                 consumes = {"application/json"})
    public ResponseEntity<?> login(@RequestBody String username, String password)throws URISyntaxException{

        User user = userService.findByName(username);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }*/

    @PostMapping(value = "/user/login",
            consumes = {"application/json"},
            produces = {"application/json"})
    public ResponseEntity<?> loginUser(HttpServletRequest httpServletRequest, @RequestParam(defaultValue = "true")
                                               boolean getaccess,
                                            @Valid
                                            @RequestBody
                                               LoginUser loginUser) throws URISyntaxException
    {

        String theToken = "";

        // return the access token
        RestTemplate restTemplate = new RestTemplate();
        String requestURI = "http://" + httpServletRequest.getServerName() + getPort(httpServletRequest) + "/login";

        List<MediaType> acceptableMediaTypes = new ArrayList<>();
        acceptableMediaTypes.add(MediaType.APPLICATION_JSON);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(acceptableMediaTypes);
        headers.setBasicAuth(System.getenv("OAUTHCLIENTID"),
                System.getenv("OAUTHCLIENTSECRET"));

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type",
                "password");
        map.add("scope",
                "read write trust");
        map.add("username",
                loginUser.getUsername());
        map.add("password",
                loginUser.getPassword());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map,
                headers);

        theToken = restTemplate.postForObject(requestURI,
                request,
                String.class);

        return new ResponseEntity<>(theToken, HttpStatus.OK);
    }

    @PostMapping(value = "/createnewuser",
                 consumes = {"application/json"},
                 produces = {"application/json"})
    public ResponseEntity<?> addNewUser(HttpServletRequest httpServletRequest,
                                        @RequestParam(defaultValue = "true")
                                                boolean getaccess,
                                        @Valid
                                        @RequestBody
                                                NewUser registerUser) throws URISyntaxException
    {
        logger.trace(httpServletRequest.getMethod()
                                       .toUpperCase() + " " + httpServletRequest.getRequestURI() + " accessed");

        // Create the user
        User newuser = new User();

        newuser.setUsername(registerUser.getUsername());
        newuser.setPassword(registerUser.getPassword());
        newuser.setEmail(registerUser.getEmail());
        newuser.setPhotourl(registerUser.getPhotourl());

        // TODO FOR NOW, ALL NEW USERS HAVE THE SAME RIGHTS AS ADMINS TO MAKE MY LIFE EASIER. WILL PROBABLY CHANGE THIS LATER.

        ArrayList<UserRoles> newRoles = new ArrayList<>();
        newRoles.add(new UserRoles(newuser,
                                   roleService.findByName("admin")));
        newuser.setUserroles(newRoles);

        newuser = userService.save(newuser);

        /* Code for some reason wouldn't show the user to get GetAllUser call from Postgres but would show the user in the H2 database
        // set the location header for the newly created resource - to another controller!
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newUserURI = ServletUriComponentsBuilder.fromUriString(httpServletRequest.getServerName() + ":" + httpServletRequest.getLocalPort() + "/users/user/{userId}")
                                                    .buildAndExpand(newuser.getUserid())
                                                    .toUri();
        responseHeaders.setLocation(newUserURI);*/

        // Code from UserController
         // set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newUserURI = ServletUriComponentsBuilder.fromCurrentRequest()
                                                    .path("/{userid}")
                                                    .buildAndExpand(newuser.getUserid())
                                                    .toUri();
        responseHeaders.setLocation(newUserURI);


        String theToken = "";
        if (getaccess)
        {
            // return the access token
            RestTemplate restTemplate = new RestTemplate();
            String requestURI = "http://" + httpServletRequest.getServerName() + getPort(httpServletRequest) + "/login";

            List<MediaType> acceptableMediaTypes = new ArrayList<>();
            acceptableMediaTypes.add(MediaType.APPLICATION_JSON);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.setAccept(acceptableMediaTypes);
            headers.setBasicAuth(System.getenv("OAUTHCLIENTID"),
                                 System.getenv("OAUTHCLIENTSECRET"));

            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("grant_type",
                    "password");
            map.add("scope",
                    "read write trust");
            map.add("username",
                    registerUser.getUsername());
            map.add("password",
                    registerUser.getPassword());

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map,
                                                                                 headers);

            theToken = restTemplate.postForObject(requestURI,
                                                  request,
                                                  String.class);
        } else
        {
            // nothing;
        }
        return new ResponseEntity<>(theToken,
                                    responseHeaders,
                                    HttpStatus.CREATED);
    }

    @ApiIgnore
    @GetMapping("favicon.ico")
    void returnNoFavicon()
    {
        logger.trace("favicon.ico endpoint accessed!");
    }
}
