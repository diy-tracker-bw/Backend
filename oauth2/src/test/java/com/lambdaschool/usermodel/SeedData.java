package com.lambdaschool.usermodel;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import com.lambdaschool.usermodel.models.*;
import com.lambdaschool.usermodel.services.ProjectService;
import com.lambdaschool.usermodel.services.RoleService;
import com.lambdaschool.usermodel.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Locale;

@Transactional
@Component
public class SeedData implements CommandLineRunner
{
    @Autowired
    RoleService roleService;

    @Autowired
    UserService userService;

    @Autowired
    ProjectService projectService;

    @Override
    public void run(String[] args) throws Exception
    {
        Role r1 = new Role("admin");
        Role r2 = new Role("user");
        Role r3 = new Role("data");

        r1 = roleService.save(r1);
        r2 = roleService.save(r2);
        r3 = roleService.save(r3);

        // admin, data, user
        ArrayList<UserRoles> admins = new ArrayList<>();
        admins.add(new UserRoles(new User(),
                r1));
        admins.add(new UserRoles(new User(),
                r2));
        admins.add(new UserRoles(new User(),
                r3));
        User u1 = new User("admin",
                "password",
                "admin@lambdaschool.local",
                admins);
//        u1.getUseremails()
//          .add(new Useremail(u1,
//                             "admin@email.local"));
//        u1.getUseremails()
//          .add(new Useremail(u1,
//                             "admin@mymail.local"));

        userService.save(u1);



        ArrayList<Project> projects = new ArrayList<>();


        User u10 = new User("patrick123",
                "password",
                "patrick123@gmail.com",
                admins);

        // Project ID is 6
        u10.getProjects().add(new Project("Photo Wall Hanger", "1.Take a wood dowel and paint it black to be used as the support. " +
                "2.Take a piece of yarn and tie it to both ends of the wood dowel. This piece of yarn will be used to support the entire wall hanging. " +
                "3.Tie three pieces of yarn between the dowel. These three pieces will be used to hold pictures. " +
                "4.Apply tape to the back of the images and place them on the yarn. " +
                "5.Hang your new wall hanger wherever you want! ", "https://www.homeyohmy.com/wp-content/uploads/2015/10/DIY-Photo-wall-hanging4.jpg",181, u10));

        u10.getProjects().add(new Project("Simple Book Ends", "1.Take any piece of plastic big enough to support books from falling. " +
                "2.Glue the piece onto the base of a chunk of wood. " +
                "3.(optional) Spray paint both the plastic and the wood. " +
                "4.Repeat steps 1-3 to create the other end of the base. " +
                "5.Place books between them. ", "https://diycandy.com/wp-content/uploads/2015/05/IMG_6221.jpg", 31, u10));

        userService.save(u10);

        User u11 = new User("kevin",
                "password",
                "kevin@gmail.com",
                admins);

        u11.getProjects().add(new Project("Pocket Tic Tac Toe", "1.Get an empty container of Altoids. " +
                "2.Cut out a piece of paper big enough to tightly fit into the altoids container. " +
                "3.Draw a Tic-Tac-Toe grid onto the piece of paper. " +
                "4.Glue the Tic-Tac-Toe grid into the altoids container. " +
                "5.Draw ten circles to be used as playing chips. " +
                "5.Draw five Xes and five Os respectively on each circle. " +
                "6.Glue each marker onto the top of magnet to give them weight. " +
                "7.Play a game with someone and have fun. ",
                "https://www.craftaholicsanonymous.net/wp-content/uploads/2016/06/DIY-pocket-tic-tac-toe-850x850.jpg", 214, u11));

        userService.save(u11);

        // data, user
        ArrayList<UserRoles> datas = new ArrayList<>();
        datas.add(new UserRoles(new User(),
                r3));
        datas.add(new UserRoles(new User(),
                r2));
        User u2 = new User("cinnamon",
                "1234567",
                "cinnamon@lambdaschool.local",
                datas);
//        u2.getUseremails()
//          .add(new Useremail(u2,
//                             "cinnamon@mymail.local"));
//        u2.getUseremails()
//          .add(new Useremail(u2,
//                             "hops@mymail.local"));
//        u2.getUseremails()
//          .add(new Useremail(u2,
//                             "bunny@email.local"));
        userService.save(u2);

        // user
        ArrayList<UserRoles> users = new ArrayList<>();
        users.add(new UserRoles(new User(),
                r2));
        User u3 = new User("barnbarn",
                "ILuvM4th!",
                "barnbarn@lambdaschool.local",
                users);
//        u3.getUseremails()
//          .add(new Useremail(u3,
//                             "barnbarn@email.local"));
        userService.save(u3);

        users = new ArrayList<>();
        users.add(new UserRoles(new User(),
                r2));
        User u4 = new User("puttat",
                "password",
                "puttat@school.lambda",
                users);
        userService.save(u4);

        users = new ArrayList<>();
        users.add(new UserRoles(new User(),
                r2));
        User u5 = new User("misskitty",
                "password",
                "misskitty@school.lambda",
                users);
        userService.save(u5);

        // using JavaFaker create a bunch of regular users
        // https://www.baeldung.com/java-faker
        // https://www.baeldung.com/regular-expressions-java

        // For now, don't use fake data.

//        FakeValuesService fakeValuesService = new FakeValuesService(new Locale("en-US"),
//                                                                    new RandomService());
//        Faker nameFaker = new Faker(new Locale("en-US"));
//
//        for (int i = 0; i < 100; i++)
//        {
//            new User();
//            User fakeUser;
//
//            users = new ArrayList<>();
//            users.add(new UserRoles(new User(),
//                                    r2));
//            fakeUser = new User(nameFaker.name()
//                                         .username(),
//                                "password",
//                                nameFaker.internet()
//                                         .emailAddress(),
//                                users);
//            fakeUser.getUseremails()
//                    .add(new Useremail(fakeUser,
//                                       fakeValuesService.bothify("????##@gmail.com")));
//            userService.save(fakeUser);
//        }
    }
}