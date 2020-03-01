<#if signIn>
    <#if !pendingChallenge??>
    <div id="player-lobby">
        <h2>Welcome ${currentUser}! You can request a game below.</h2>
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
            <h2>${challengeUser!"Error"} has challenged you to a game!</h2>
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