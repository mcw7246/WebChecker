package com.webcheckers.ui;

import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

public class GetHomeRouteTest
{
    /**
     * The unit test for the {@link GetHomeRoute}
     *
     * @author Zehra Amena Baig 'zab1166'
     */

    private GetHomeRoute CuT;

    private PlayerLobby lobby;

    private Request request;
    private Session session;
    private Response response;
    private TemplateEngine engine;

    @BeforeEach
    public void setup()
    {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        engine = mock(TemplateEngine.class);
        response = mock(Response.class);
        lobby = new PlayerLobby();
        //Create a unique CuT for each test.
        CuT = new GetHomeRoute(engine, lobby);
    }
}
