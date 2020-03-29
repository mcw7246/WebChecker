package com.webcheckers.ui;

import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.TemplateEngine;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

/**
 * The unit test suite for the {@link GetSignInRoute} component.
 *
 * @author Sean Bergen 'sdb2139'
 */
public class GetSignInRouteTest
{

    /**
     * GetSignInRoute can be summarized as:
     *
     * Attributes
     *   -  LOG              Logger
     *   -  SIGNIN_MSG       Message
     *   -  templateEngine   TemplateEngine
     *
     * Methods
     *   +  handle           Object
     */



    /**
     * The component under test.
     * <p>
     * Stateless component that actually test the functionality of the
     * {@link GetSignInRoute}
     */
    private GetSignInRoute CuT;

    // Friendly objects
    private TemplateEngine templateEngine;
    private Request request;
    private Response response;
    private PlayerLobby lobby;
    private Player player;


    @BeforeEach
    public void setup()
    {
        templateEngine = mock(TemplateEngine.class);
        lobby = new PlayerLobby();
        player = new Player(lobby);
    }

    /**
     * Tests that the main constrcutor works without failure.
     */
    @Test
    public void ctor_withArg()
    {
        CuT = new GetSignInRoute(templateEngine);
    }

    /**
     * Tests handle
     */
    @Test
    public void testHandle()
    {
        CuT = new GetSignInRoute(templateEngine);


        assertTrue(lobby.getUsernames() == null);

        CuT.handle(request, response);

        //Check that the request was sent correctly
        //assertTrue(request.queryParams(), );
    }
}
