package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameManager;
import com.webcheckers.application.PlayerLobby;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

import java.util.Objects;

public class PostValidateMoveRoute implements Route
{
    private final TemplateEngine templateEngine;
    private final Gson gson;
    private final PlayerLobby playerLobby;
    private final GameManager gameManager;


    public PostValidateMoveRoute(TemplateEngine templateEngine, Gson gson, PlayerLobby playerLobby, GameManager gameManager) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        this.gson = Objects.requireNonNull(gson, "gson is required");
        this.playerLobby = Objects.requireNonNull(playerLobby, "playerLobby is required");
        this.gameManager = Objects.requireNonNull(gameManager, "gameManager is required");
    }


    @Override
    public Object handle(Request request, Response response)
    {

        return null;
    }
}
