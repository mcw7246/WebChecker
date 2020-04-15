<#if signIn>
    <#if !pendingChallenge??>
    <div id="player-lobby">
        <h2>Welcome ${currentUser}! You can request to play a game with a
            player below, or watch an ongoing match!</h2>
        <h3>Challenge a Player:</h3>
        <#list usernames as username>
                <p>${username}:
                <form action='./requestGame' method="POST">
                <button type="submit" name="gameRequest"
                value=${username}>Request
                    Game</button>
            </form>
        <#else>
            No other players are currently logged in. ):
        </#list>
    </div>
    <#else>
        <div id="challenge-pending">
            <h2>You have been challenged to a game.</h2>
            <h3>${challengeUser!"Error"} has challenged you!</h3>
            <form action='./requestResponse' method="Post">
                <button type="submit" name="gameAccept"
                        value="yes">Accept</button>
            </form>

            <form action='./requestResponse' method="Post">
                <button type="submit" name="gameAccept" value="no">Deny</button>
            </form>
        </div>
    </#if>
<#else>
    <div id="player-lobby">Currently logged in Players: ${playerNum}</div>
</#if>