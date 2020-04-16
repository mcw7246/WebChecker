<#if signIn>
        <div id="active-games">
            <h3>Watch an Ongoing Match:</h3>
            <#list games as game>
                <p>${game.getRedPlayer().getUsername()} vs. ${game
                    .getWhitePlayer().getUsername()}:
                <form action='./spectator/game' method="GET">
                    <button type="submit" name="watchGameRequest"
                            value=${game.getRedPlayer().getUsername()
                    ?replace(" ", "-")}>Watch
                        Game</button>
                </form>
            <#else>
                Currently no active games!
            </#list>
        </div>
<#else>
    <div id="player-lobby">Currently Active Games: ${gameNum}</div>
</#if>