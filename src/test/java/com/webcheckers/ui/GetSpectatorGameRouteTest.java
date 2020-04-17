package com.webcheckers.ui;

import com.webcheckers.application.GameManager;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.application.ReplayManager;
import com.webcheckers.model.Board;
import com.webcheckers.model.CheckerGame;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GetSpectatorGameRouteTest
{
  private GetSpectatorGameRoute CuT;

  private GameManager manager;
  private Player player1;
  private Player player2;
  private ReplayManager rManager;




  private TemplateEngine engine;
  private CheckerGame game;
  private PlayerLobby lobby;
  private Session session;
  private Request request;
  private Response response;
  private Board board;

  @BeforeEach
  public void setUp(){
    rManager = new ReplayManager();
    player1 = new Player(lobby);
    player1.setUsername("player1");
    player2 = new Player(lobby);
    player2.setUsername("player2");
    board = new Board();
    game = new CheckerGame(player1, player2, board);



    engine = mock(TemplateEngine.class);
    manager = mock(GameManager.class);
    session = mock(Session.class);
    lobby = mock(PlayerLobby.class);
    request = mock(Request.class);
    response = mock(Response.class);
    when(request.session()).thenReturn(session);
    when(request.queryParams("watchGameRequest")).thenReturn(player1.getUsername());

    CuT = new GetSpectatorGameRoute(engine);
  }

  @Test
  public void testSpectator(){
    int gameID = manager.getGameID(player1.getUsername());

    when(manager.getGameID(player1.getUsername())).thenReturn(gameID);
    when(manager.getGame(gameID)).thenReturn(game);
    when(session.attribute(GetHomeRoute.PLAYER_KEY)).thenReturn(player1);
    when(session.attribute(GetHomeRoute.GAME_MANAGER_KEY)).thenReturn(manager);

    String status = manager.getGameOverStatus(gameID);
    System.out.println(status);
    when(manager.getGameOverStatus(gameID)).thenReturn(status);

    CuT.handle(request, response);
    assertNotNull(player1);
  }
}
