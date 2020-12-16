package com.laurenemick.pintreach;

import com.laurenemick.pintreach.models.*;
import com.laurenemick.pintreach.services.ArticleService;
import com.laurenemick.pintreach.services.BoardService;
import com.laurenemick.pintreach.services.RoleService;
import com.laurenemick.pintreach.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class SeedData implements CommandLineRunner
{
    @Autowired
    RoleService roleService;

    @Autowired
    UserService userService;

    @Autowired
    ArticleService articleService;

    @Autowired
    BoardService boardService;

    @Override
    public void run(String[] args) throws Exception
    {
        Role r1 = new Role("admin");
        Role r2 = new Role("user");
        Role r3 = new Role("data");

        r1 = roleService.save(r1);
        r2 = roleService.save(r2);
        r3 = roleService.save(r3);

        // url, title, author, source, publishedAt, urlToImage, content, description
        Article a1 = new Article("http", "World War II", "History.com editors", "History", "12/16/20", "", "https://www.history.com/topics/world-war-ii/world-war-ii-history", "");
        Article a2 = new Article("http", "Nuclear", "", "Institute for Energy Research", "12/01/17", "", "https://www.instituteforenergyresearch.org/?encyclopedia=nuclear&gclid=CjwKCAiA_eb-BRB2EiwAGBnXXjJk4Y278Ze_GNN6994HVRPaY7JyazyscknMw_V1Qzdmf8bkVYYMPRoC7l8QAvD_BwE", "Nuclear power comes from the process of nuclear fission, or the splitting of atoms. The resulting controlled nuclear chain reaction creates heat, which is used to boil water, produce steam, and drive turbines that generate electricity.");

        a1 = articleService.save(a1);
        a2 = articleService.save(a2);

        // admin, data, user
        User u1 = new User("admin",
            "password",
            "admin@admin.com",
            "http");

        u1.getRoles()
            .add(new UserRoles(u1, r1));
        u1.getRoles()
            .add(new UserRoles(u1, r2));
        u1.getRoles()
            .add(new UserRoles(u1, r3));

        Board b1 = new Board("Nuclear Energy", "all things nuclear", "http", u1);
        b1.getArticles().add(new BoardArticles(b1, a2));

        userService.save(u1);
        boardService.save(b1, a2);
        boardService.save(u1, a2);

        // data, user
        User u2 = new User("laurenemick",
            "password",
            "lauren@emick.com",
            "http");

        u2.getRoles()
            .add(new UserRoles(u2, r2));
        u2.getRoles()
            .add(new UserRoles(u2, r3));

        Board b2 = new Board("News", "News articles in year 2020", "http", u2);
        b2.getArticles().add(new BoardArticles(b2, a2));

        Board b3 = new Board("WWII Research", "Freshman - HIST 101", "http", u2);
        b3.getArticles().add(new BoardArticles(b3, a1));

        userService.save(u2);
        boardService.save(b2, a2);
        boardService.save(u2, a2);
        boardService.save(b3, a1);
        boardService.save(u2, a1);
    }
}