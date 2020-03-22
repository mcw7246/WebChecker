package com.webcheckers.ui;

import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Session;
import spark.TemplateEngine;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
/**
 * The {@code POST /signin} route handler.
 *
 * @author Mikayla Wishart 'mcw7246'
 */
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
  private PlayerLobby playerLobby;
  /*
   *mock objects
   */

  private Request request;
  private Session session;
  private TemplateEngine engine;

  @BeforeEach
  public void setup()
  {
    playerLobby = new PlayerLobby();
    player = new Player(playerLobby);


    request = mock(Request.class);
    session = mock(Session.class);
    when(request.session()).thenReturn(session);
    engine = mock(TemplateEngine.class);



    CuT = new PostSignInRoute(engine,playerLobby);
  }


  @Test
  public void testMake_invalid_argument_message()
  {
    when(session.attribute(GetHomeRoute.SIGN_IN_KEY)).thenReturn(player);

  }
}
