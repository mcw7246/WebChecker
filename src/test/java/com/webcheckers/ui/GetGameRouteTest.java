package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameManager;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.application.ReplayManager;
import com.webcheckers.model.Board;
import com.webcheckers.model.CheckerGame;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import spark.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import static com.webcheckers.ui.GetReplayGameRoute.MODE_OPTIONS_AS_JSON;
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
   * @author Mikayla Wishart 'mcw7246'
   */

  private static final String RED_WINS_BOARD = "src/test/java/com/webcheckers" +
          "/test-boards/NoMoreMovesRed.JSON";

  private static final String USERNAME = "username";
  private static final int gameID = 69;
  Gson gson = new Gson();
  private String RED_PLAYER = "redPlayer";
  //The component under test (CuT)
  private GetGameRoute CuT;
  //Friendly objects
  private PlayerLobby lobby;
  private GameManager manager;
  private Player player1, player2;
  private ReplayManager rManager;
  private CheckerGame game;
  private Board board;

  //Mock objects
  private Request request;
  private Session session;
  private Response response;
  private TemplateEngine engine;

  /**
   * Setup necessary mock objects and associations for testing
   */
  @BeforeEach
  public void setup()
  {

    lobby = new PlayerLobby();
    rManager = new ReplayManager();
    manager = new GameManager(lobby, rManager);
    player1 = new Player(lobby);
    player1.setUsername("player1");
    player2 = new Player(lobby);
    player2.setUsername("player2");

    request = mock(Request.class);
    session = mock(Session.class);
    when(request.session()).thenReturn(session);
    response = mock(Response.class);
    engine = mock(TemplateEngine.class);

    CuT = new GetGameRoute(engine);
  }

  /**
   * Ensure that the game is retrieved for player one.
   */
  @Test
  public void playerOneTest()
  {
    lobby.newPlayer(player1);
    lobby.newPlayer(player2);
    manager.startGame(player1.getUsername(), player2.getUsername());

    when(request.session()).thenReturn(session);
    when(session.attribute(GetHomeRoute.PLAYER_KEY)).thenReturn(player1);
    when(session.attribute(GetHomeRoute.GAME_MANAGER_KEY)).thenReturn(manager);

    TemplateEngineTest testHelper = new TemplateEngineTest();
    when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

    CuT.handle(request, response);
    testHelper.assertViewModelAttribute(GetGameRoute.RED_PLAYER, player1);
  }

  /**
   * Ensure that the game is retrieved for player 2.
   */
  @Test
  public void playerTwoTest()
  {
    lobby.newPlayer(player1);
    lobby.newPlayer(player2);
    manager.startGame(player1.getUsername(), player2.getUsername());

    when(request.session()).thenReturn(session);
    when(session.attribute(GetHomeRoute.PLAYER_KEY)).thenReturn(player2);
    when(session.attribute(GetHomeRoute.GAME_MANAGER_KEY)).thenReturn(manager);

    TemplateEngineTest testHelper = new TemplateEngineTest();
    when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

    CuT.handle(request, response);
    testHelper.assertViewModelAttribute(GetGameRoute.RED_PLAYER, player1);
  }

  /**
   * Ensure that if the player object is null, that the user is redirected home.
   */
  @Test
  public void nullPlayerTest()
  {
    lobby.newPlayer(player1);
    lobby.newPlayer(player2);
    manager.startGame(player1.getUsername(), player2.getUsername());

    when(request.session()).thenReturn(session);
    when(session.attribute(GetHomeRoute.PLAYER_KEY)).thenReturn(null);
    when(session.attribute(GetHomeRoute.GAME_MANAGER_KEY)).thenReturn(manager);

    TemplateEngineTest testHelper = new TemplateEngineTest();
    when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

    assertEquals("Home Redirect", CuT.handle(request, response));
  }

  /**
   * Ensure that if the checker game object is null, that the user is redirected
   * to the homepage,
   */
  @Test
  public void null_game_test()
  {
    when(request.session()).thenReturn(session);
    when(session.attribute(GetHomeRoute.PLAYER_KEY)).thenReturn(player1);
    GameManager MANAGER = mock(GameManager.class);
    when(session.attribute(GetHomeRoute.GAME_MANAGER_KEY)).thenReturn(MANAGER);
    when(MANAGER.getGame(any(Integer.class))).thenReturn(null);

    assertNull(CuT.handle(request, response));
  }

  @Test
  public void cannot_move()
  {
    assertNotNull(CuT.handle(request, response));
  }

  @Test
  public void test_you_won()
  {
    try
    {
      board = gson.fromJson(new FileReader(RED_WINS_BOARD), Board.class);
    }
    catch (FileNotFoundException e)
    {
      fail("ERROR: FILE NOT FOUND STARTING GAME FROM SCRATCH");
      board = new Board();
    }

    GameManager manager = mock(GameManager.class);
    game = new CheckerGame(player1, player2, board);

    int gameID = manager.getGameID(player1.getUsername());

    int redPieces = game.getNumRedPieces();
    int whitePieces = game.getNumWhitePieces();
    when(session.attribute(GetHomeRoute.PLAYER_KEY)).thenReturn(player1);
    when(session.attribute(GetHomeRoute.GAME_MANAGER_KEY)).thenReturn(manager);
    when(manager.getGame(gameID)).thenReturn(game);
    when(manager.getOpponent(player1.getUsername())).thenReturn(player2);
    System.out.println(manager.getGameID(player1.getUsername()));
    System.out.println(whitePieces);
    System.out.println(redPieces);
    assertEquals(0, redPieces);
    Map<String, Object> modeOptions = new HashMap<>();


    //CuT.handle(request, response);

    System.out.println();
    TemplateEngineTest testHelper = new TemplateEngineTest();
    //testHelper.assertViewModelAttribute(MODE_OPTIONS_AS_JSON, gson.toJson(modeOptions));
    //testHelper.assertViewModelAttribute(GetGameRoute.RED_PLAYER, redPlayer);
    //when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());


    //when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
  }

}