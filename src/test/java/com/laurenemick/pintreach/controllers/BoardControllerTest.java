package com.laurenemick.pintreach.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laurenemick.pintreach.models.*;
import com.laurenemick.pintreach.services.BoardService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // bc of security
@AutoConfigureMockMvc // security
@WithMockUser(username = "admin", roles = {"ADMIN", "USER", "DATA"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BoardControllerTest
{
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private BoardService boardService;

    List<Board> boardList = new ArrayList<>();

    List<User> userList = new ArrayList<>();

    @Before
    public void setUp() throws Exception
    {
        Role r1 = new Role("admin");
        Role r2 = new Role("user");
        Role r3 = new Role("data");

        Article a1 = new Article("http", "World War II", "History.com editors", "History", "12/16/20", "", "https://www.history.com/topics/world-war-ii/world-war-ii-history", "");
        Article a2 = new Article("http", "Nuclear", "", "Institute for Energy Research", "12/01/17", "", "https://www.instituteforenergyresearch.org/?encyclopedia=nuclear&gclid=CjwKCAiA_eb-BRB2EiwAGBnXXjJk4Y278Ze_GNN6994HVRPaY7JyazyscknMw_V1Qzdmf8bkVYYMPRoC7l8QAvD_BwE", "Nuclear power comes from the process of nuclear fission, or the splitting of atoms. The resulting controlled nuclear chain reaction creates heat, which is used to boil water, produce steam, and drive turbines that generate electricity.");

        // admin, data, user
        User u1 = new User("admin",
            "password",
            "admin@admin.com",
            "http");
        u1.setUserid(17);

        u1.getRoles()
            .add(new UserRoles(u1, r1));
        u1.getRoles()
            .add(new UserRoles(u1, r2));
        u1.getRoles()
            .add(new UserRoles(u1, r3));

        Board b1 = new Board("Nuclear Energy", "all things nuclear", "http", u1);
        b1.getArticles().add(new BoardArticles(b1, a2));
        b1.setBoardid(100);

        // data, user
        User u2 = new User("laurenemick",
            "password",
            "lauren@emick.com",
            "http");
        u2.setUserid(18);

        u2.getRoles()
            .add(new UserRoles(u2, r2));
        u2.getRoles()
            .add(new UserRoles(u2, r3));

        Board b2 = new Board("News", "News articles in year 2020", "http", u2);
        b2.getArticles().add(new BoardArticles(b2, a2));
        b2.setBoardid(101);

        Board b3 = new Board("WWII Research", "Freshman - HIST 101", "http", u2);
        b3.getArticles().add(new BoardArticles(b3, a1));
        b3.setBoardid(102);

        // add to list
        userList.add(u1);
        userList.add(u2);
        boardList.add(b1);
        boardList.add(b2);
        boardList.add(b3);

        // add the below to do security testing:
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply(SecurityMockMvcConfigurers.springSecurity())
            .build();
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void a_listAllBoards() throws Exception
    {
        String apiURL = "/boards/user/17";

        Mockito.when(boardService.findAllByUserId(17)).thenReturn(boardList);

        RequestBuilder rb = MockMvcRequestBuilders
            .get(apiURL)
            .accept(MediaType.APPLICATION_JSON);

        // the following actually performs a real controller call
        MvcResult r = mockMvc.perform(rb).andReturn(); // this could throw an exception
        String tr = r.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(boardList);

        System.out.println("Expect: " + er);
        System.out.println("Actual: " + tr);

        assertEquals("Rest API Returns List", er, tr);
    }

    @Test
    public void b_getBoardById() throws Exception
    {
        String apiURL = "/boards/board/100";

        Mockito.when(boardService.findBoardById(100)).thenReturn(boardList.get(0));

        RequestBuilder rb = MockMvcRequestBuilders
            .get(apiURL)
            .accept(MediaType.APPLICATION_JSON);
        MvcResult r = mockMvc.perform(rb).andReturn(); // this could throw an exception
        String tr = r.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(boardList.get(0));

        System.out.println("Expect: " + er);
        System.out.println("Actual: " + tr);

        assertEquals("Rest API Returns List", er, tr);
    }

    @Test
    public void c_addNewBoard() throws Exception
    {
        String apiURL = "/boards/board";

        Board newBoard = new Board();

        newBoard.setBoardid(100);
        newBoard.setUser(userList.get(0));
        newBoard.setTitle("New Board");
        newBoard.setDescription("New Board description.");
        newBoard.setThumbnail("http");
        newBoard.setBoardid(0);

        ObjectMapper mapper = new ObjectMapper();
        String itemString = mapper.writeValueAsString(newBoard);

        Mockito.when(boardService.save(any(Board.class))).thenReturn(newBoard);

        RequestBuilder rb = MockMvcRequestBuilders
            .post(apiURL)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(itemString);

        mockMvc
            .perform(rb)
            .andExpect(status().isCreated())
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void d_updateBoard() throws Exception
    {
        String apiURL = "/boards/board/100";

        Board newBoard = new Board();
        newBoard.setBoardid(100);
        newBoard.setUser(userList.get(0));
        newBoard.setTitle("Nuclear Energy");
        newBoard.setDescription("All things nuclear");
        newBoard.setThumbnail("http");

        Mockito.when(boardService.update(100, newBoard)).thenReturn(boardList.get(0));

        ObjectMapper mapper = new ObjectMapper();
        String boardString = mapper.writeValueAsString(newBoard);

        RequestBuilder rb = MockMvcRequestBuilders
            .put(apiURL, 7)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(boardString);

        mockMvc.perform(rb)
            .andExpect(status().is2xxSuccessful())
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void e_deleteFromBoard() throws Exception
    {
        String apiURL = "/boards/board/100";

        RequestBuilder rb = MockMvcRequestBuilders.delete(apiURL, 100)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(rb)
            .andExpect(status().is2xxSuccessful())
            .andDo(MockMvcResultHandlers.print());
    }
}