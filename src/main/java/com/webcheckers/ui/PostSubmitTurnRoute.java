package com.webcheckers.ui;

import com.webcheckers.application.GameManager;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Move;
import com.webcheckers.model.Piece;
import com.webcheckers.model.Player;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

/**
 * Submits the user turn. This is only active when the game view is in a
 * Stable Turn state.
 * The response body must be a message that has INFO type if the turn as a
 * whole is valid and teh server has processed the turn. It refreshes the
 * page using a 'GET /game' URL.
 *
 * @author Austin Miller 'akm8654'
 */
public class PostSubmitTurnRoute implements Route
{
  private PlayerLobby playerLobby;
  private GameManager gameManager;
  public PostSubmitTurnRoute(final PlayerLobby playerLobby, final GameManager gameManager){
    this.gameManager = gameManager;
    this.playerLobby = playerLobby;
  }

  @Override
  public Object handle(Request request, Response response){
    Piece.Color activeColor;
    final Session httpsSession = request.session();
    activeColor = httpsSession.attribute(GetGameRoute.ACTIVE_COLOR);



    //changes the active color
    if(activeColor == null || activeColor == Piece.Color.RED){
      httpsSession.attribute(GetGameRoute.ACTIVE_COLOR, Piece.Color.WHITE);
    }else{
      httpsSession.attribute(GetGameRoute.ACTIVE_COLOR, Piece.Color.RED);
    }
    activeColor = httpsSession.attribute(GetGameRoute.ACTIVE_COLOR);
    return null;
  }

}
