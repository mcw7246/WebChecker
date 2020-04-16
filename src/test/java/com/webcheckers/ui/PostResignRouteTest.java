package com.webcheckers.ui;

import com.webcheckers.application.GameManager;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.CheckerGame;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Null;
import spark.*;

import java.util.Map;
import java.util.Set;

import static com.webcheckers.ui.PostRequestGameRoute.MESSAGE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

/**
 * The unit test for the {@link PostRequestResponseRoute}
 *
 * @author Sean Bergen 'sdb2139'
 */
public class PostResignRouteTest
{
    private static final String PLAYER1 = "player1Username";
    private static final String PLAYER2 = "player2Username";

    private static final String VALID_RESIGN = "{\"text\":\"Resign Successful\",\"type\":\"INFO\"}";

    private Player player1;
    private Player player2;

    private Request request;
    private Session session;
    private Response response;
    private CheckerGame game;

    private GameManager manager;

    private TemplateEngine templateEngine;


    /**
     * the component under testing
     */
    private PostResignRoute CuT;

    @BeforeEach
    public void setup()
    {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        player1 = mock(Player.class);
        player2 = mock(Player.class);
        game = mock(CheckerGame.class);
        when(session.attribute(GetHomeRoute.PLAYER_KEY)).thenReturn(player1);
        manager = mock(GameManager.class);
        when(session.attribute(GetHomeRoute.GAME_MANAGER_KEY)).thenReturn(manager);
        when(player1.getUsername()).thenReturn(PLAYER1);
        when(manager.getGameID(PLAYER1)).thenReturn(1);
        this.CuT = new PostResignRoute(templateEngine);
    }

    /**
     * Tests if the constructor works
     */
    @Test
    public void ctor_withArg()
    {
        CuT = new PostResignRoute(templateEngine);
    }


    /**
     * Test for the handle method for resign routes
     * Tests that the return from handle is a valid resign message
     */
    @Test
    public void testHandle()
    {
        try
        {
            //System.out.println(CuT.handle(request,response).toString());
            //System.out.println(VALID_RESIGN);
            assertEquals(CuT.handle(request,response).toString(), VALID_RESIGN);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
