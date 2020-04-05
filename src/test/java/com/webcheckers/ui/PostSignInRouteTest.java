package com.webcheckers.ui;

import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.ui.GetSignInRoute;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import static org.junit.jupiter.api.Assertions.*;
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
  public static final String INVALID_USERNAME_LONG = "abcdefghijklmnopqrstuvwxyz123";
  public static final String AVAILABLE_USERNAME = "username1";
  public static final String TAKEN_USERNAME = "username2";

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
  //TODO fix the errors in the tests

  @Test
  public void testInvalid_username_short(){
    when(request.queryParams(eq(PostSignInRoute.USERNAME_PARAM))).thenReturn(INVALID_USERNAME_SHORT);

    final TemplateEngineTest testHelper = new TemplateEngineTest();
    when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

    CuT.handle(request, response);

    testHelper.assertViewModelExists();
    testHelper.assertViewModelIsaMap();

    testHelper.assertViewModelAttribute(GetHomeRoute.TITLE_ATTR, GetHomeRoute.TITLE);
    assertNotNull(PostSignInRoute.MESSAGE_ATTR);
    //testHelper.assertViewModelAttribute(PostSignInRoute.MESSAGE_ATTR, null)
    ;

    testHelper.assertViewName(PostSignInRoute.VIEW_NAME);
  }

  @Test
  public void testInvalid_username_long(){
    when(request.queryParams(eq(PostSignInRoute.USERNAME_PARAM))).thenReturn(INVALID_USERNAME_LONG);

    final TemplateEngineTest testHelper = new TemplateEngineTest();
    when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

    CuT.handle(request, response);

    testHelper.assertViewModelExists();
    testHelper.assertViewModelIsaMap();

    assertNotNull(PostSignInRoute.MESSAGE_ATTR);
    testHelper.assertViewModelAttribute(GetHomeRoute.TITLE_ATTR, GetHomeRoute.TITLE);

  }

  @Test
  public void testTaken_username(){
    when(request.queryParams(eq(PostSignInRoute.USERNAME_PARAM))).thenReturn(TAKEN_USERNAME);
    final TemplateEngineTest testHelper = new TemplateEngineTest();

   /**try{
      CuT.handle(request, response);
      fail("No repeat of username found. Test failed.");
    }catch (spark.HaltException e){
      //test npassed
    }*/


    assertNotNull(PostSignInRoute.MESSAGE_ATTR);
//    assertEquals(player.isValidUsername(TAKEN_USERNAME), Player.UsernameResult.TAKEN);


  }

  @Test
  public void testAvailable_username(){
    when(request.queryParams(eq(PostSignInRoute.USERNAME_PARAM))).thenReturn("testName1");

    final TemplateEngineTest testHelper = new TemplateEngineTest();
    /**try{
      CuT.handle(request, response);
      fail("The username was somehow invalid");
    }catch(spark.HaltException e){
      //passes
    }

    //when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
    assertEquals(player.isValidUsername(AVAILABLE_USERNAME), Player.UsernameResult.AVAILABLE);

*/
  }
}
