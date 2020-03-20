package com.webcheckers.ui;

import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import spark.Request;
import spark.Session;
import spark.TemplateEngine;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("UI-tier")
public class PostSignInRouteTest
{

  /*
   *The component-under-test (CuT)
   */
  private PostSignInRoute CuT;

  /*
   *friendly objects
   */
  private Player player;

  /*
   *mock objects
   */
  private PlayerLobby playerLobby;
  private Request request;
  private Session session;
  private TemplateEngine engine;

  @BeforeEach
  public void setup()
  {
    player = new Player(playerLobby);

    request = mock(Request.class);
    session = mock(Session.class);
    when(request.session()).thenReturn(session);
    engine = mock(TemplateEngine.class);
    playerLobby = mock(PlayerLobby.class);


    CuT = new PostSignInRoute(engine,playerLobby);
  }

}
