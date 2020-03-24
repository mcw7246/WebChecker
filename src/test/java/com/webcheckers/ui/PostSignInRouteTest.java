package com.webcheckers.ui;

import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.ui.GetSignInRoute;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import static com.webcheckers.util.Message.error;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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
  public static final String INVALID_USERNAME_SHORT = "name1";

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
  private Response response;
  @BeforeEach
  public void setup()
  {
    playerLobby = new PlayerLobby();
    player = new Player(playerLobby);
    response = mock(Response.class);
    request = mock(Request.class);
    session = mock(Session.class);
    when(request.session()).thenReturn(session);
    engine = mock(TemplateEngine.class);



    CuT = new PostSignInRoute(engine,playerLobby);
  }


  @Test
  public void testInvalid_username_short(){
    when(request.queryParams(eq(PostSignInRoute.USERNAME_PARAM))).thenReturn(INVALID_USERNAME_SHORT);

    final TemplateEngineTest testHelper = new TemplateEngineTest();
    when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

    CuT.handle(request, response);

    testHelper.assertViewModelExists();
    testHelper.assertViewModelIsaMap();

    testHelper.assertViewModelAttribute(GetHomeRoute.TITLE_ATTR, GetHomeRoute.TITLE);

    //testHelper.assertViewModelAttribute(PostSignInRoute.MESSAGE_ATTR, error(PostSignInRoute.makeInvalidArgMessage(INVALID_USERNAME_SHORT)))
    ;

    testHelper.assertViewName(PostSignInRoute.VIEW_NAME);
  }

  @Test
  public void testInvalid_username_long(){


  }
}
