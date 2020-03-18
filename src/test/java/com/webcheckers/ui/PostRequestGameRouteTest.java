package com.webcheckers.ui;

import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("UI-tier")
public class PostRequestGameRouteTest
{

  /**
   * The component-under-rest (CuT).
   *
   * This is a stateless component so the only one needed.
   * The {@link Player} component is considered a "friendly" dependency.
   */
  private PostRequestGameRoute CuT;

  //friendly objects
  private Player player1;
  private Player player2;

  // attributes holding mock objects
  private Request request;
  private Session session;
  private Response response;
  private TemplateEngine engine;
  private PlayerLobby lobby;

  @BeforeEach
  public void setup(){
    request = mock(Request.class);
    session = mock(Session.class);
    when(request.session()).thenReturn(session);
    engine = mock(TemplateEngine.class);
    response = mock(Response.class);

    lobby = mock(PlayerLobby.class);

  }

}
