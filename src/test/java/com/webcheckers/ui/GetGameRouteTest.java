package com.webcheckers.ui;

import com.webcheckers.application.GameManager;
import com.webcheckers.application.PlayerLobby;
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
    private GameManager gameManager;
    private Player player1, player2;

    //Mock objects
    private Request request;
    private Session session;
    private Response response;
    private TemplateEngine engine;

    @BeforeEach
    public void setup()
    {
        lobby = new PlayerLobby();
        gameManager = new GameManager(lobby);
        player1 = new Player(lobby);
        player1.setUsername("player1");
        player2 = new Player(lobby);
        player2.setUsername("player2");

        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        engine = mock(TemplateEngine.class);

        CuT = new GetGameRoute(engine, lobby, gameManager);
    }

    @Test
    public void playerOneTest()
    {
        lobby.newPlayer(player1);
        lobby.newPlayer(player2);
        gameManager.startGame(player1.getUsername(), player2.getUsername());

        when(request.session()).thenReturn(session);
        when(session.attribute(GetHomeRoute.PLAYER_KEY)).thenReturn(player1);

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
        gameManager.startGame(player1.getUsername(), player2.getUsername());

        when(request.session()).thenReturn(session);
        when(session.attribute(GetHomeRoute.PLAYER_KEY)).thenReturn(player2);

        TemplateEngineTest testHelper = new TemplateEngineTest();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request, response);
        testHelper.assertViewModelAttribute(GetGameRoute.RED_PLAYER, player2);
    }

}