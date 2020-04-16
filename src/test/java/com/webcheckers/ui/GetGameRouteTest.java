package com.webcheckers.ui;

import com.webcheckers.application.GameManager;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.application.ReplayManager;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

@Tag("UI-tier")
public class GetGameRouteTest
{
    /**
     * The unit test for the {@link GetGameRoute}
     *
     * @author Zehra Amena Baig 'zab1166'
     */

    //The component under test (CuT)
    private GetGameRoute CuT;

    //Friendly objects
    private PlayerLobby lobby;
    private GameManager manager;
    private Player player1, player2;
    private ReplayManager rManager;

    //Mock objects
    private Request request;
    private Session session;
    private Response response;
    private TemplateEngine engine;

    @BeforeEach
    public void setup()
    {
        lobby = new PlayerLobby();
        rManager = new ReplayManager();
        manager = new GameManager(lobby, rManager);
        player1 = new Player(lobby);
        player1.setUsername("player1");
        player2 = new Player(lobby);
        player2.setUsername("player2");

        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        engine = mock(TemplateEngine.class);

        CuT = new GetGameRoute(engine);
    }

    @Test
    public void playerOneTest()
    {
        lobby.newPlayer(player1);
        lobby.newPlayer(player2);
        manager.startGame(player1.getUsername(), player2.getUsername());

        when(request.session()).thenReturn(session);
        when(session.attribute(GetHomeRoute.PLAYER_KEY)).thenReturn(player1);
        when(session.attribute(GetHomeRoute.GAME_MANAGER_KEY)).thenReturn(manager);

        TemplateEngineTest testHelper = new TemplateEngineTest();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request, response);
        testHelper.assertViewModelAttribute(GetGameRoute.RED_PLAYER, player1);
    }

    @Test
    public void playerTwoTest()
    {
        lobby.newPlayer(player1);
        lobby.newPlayer(player2);
        manager.startGame(player1.getUsername(), player2.getUsername());

        when(request.session()).thenReturn(session);
        when(session.attribute(GetHomeRoute.PLAYER_KEY)).thenReturn(player2);
        when(session.attribute(GetHomeRoute.GAME_MANAGER_KEY)).thenReturn(manager);

        TemplateEngineTest testHelper = new TemplateEngineTest();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request, response);
        testHelper.assertViewModelAttribute(GetGameRoute.RED_PLAYER, player1);
    }

    @Test
    public void nullPlayerTest()
    {
        lobby.newPlayer(player1);
        lobby.newPlayer(player2);
        manager.startGame(player1.getUsername(), player2.getUsername());

        when(request.session()).thenReturn(session);
        when(session.attribute(GetHomeRoute.PLAYER_KEY)).thenReturn(null);
        when(session.attribute(GetHomeRoute.GAME_MANAGER_KEY)).thenReturn(manager);

        TemplateEngineTest testHelper = new TemplateEngineTest();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        assertEquals("Home Redirect", CuT.handle(request, response));
    }

    @Test
    public void cannot_move()
    {
        //TODO
    }

}