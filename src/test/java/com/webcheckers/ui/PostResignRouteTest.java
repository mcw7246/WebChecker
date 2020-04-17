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
import spark.utils.Assert;

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

    private static final String VALID_RESIGN = "{\"text\":\"Resign Successful\",\"type\":\"INFO\"}";

    private Player player1;

    private Request request;
    private Session session;
    private Response response;

    private GameManager manager;

    private Request badRequest;
    private Session badSession;
    private GameManager badManager;

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
        when(session.attribute(GetHomeRoute.PLAYER_KEY)).thenReturn(player1);
        manager = mock(GameManager.class);
        when(manager.getGameOverStatus(1)).thenReturn("No");
        when(session.attribute(GetHomeRoute.GAME_MANAGER_KEY)).thenReturn(manager);
        when(player1.getUsername()).thenReturn(PLAYER1);
        when(manager.getGameID(PLAYER1)).thenReturn(1);
        this.CuT = new PostResignRoute();
    }

    /**
     * Tests if the constructor works
     */
    @Test
    public void ctor_withArg()
    {
        CuT = new PostResignRoute();
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

    /**
     * Test for the handle method for resign routes
     * Tests that the return from handle is not valid resign message
     * since the status of game is bad
     */
    @Test
    public void testBadGame()
    {
        try
        {
            badManager = mock(GameManager.class);
            when(badManager.getGameOverStatus(1)).thenReturn("lmao this is bad message");
            when(session.attribute(GetHomeRoute.GAME_MANAGER_KEY)).thenReturn(badManager);
            assertNotEquals(VALID_RESIGN, CuT.handle(request,response).toString());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Tests that the valid resign message will not be given
     * if the player is null
     */
    @Test
    public void testNoPlayer()
    {
        badRequest = mock(Request.class);
        when(badRequest.session()).thenReturn(badSession);
        badSession = mock(Session.class);
        when(badSession.attribute(PLAYER1)).thenReturn(null);
        try
        {
            assertNotEquals(CuT.handle(badRequest,response), VALID_RESIGN);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
