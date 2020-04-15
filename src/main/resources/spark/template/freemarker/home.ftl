<!DOCTYPE html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
    <meta http-equiv="refresh" content="10">
    <title>Web Checkers | ${title}</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>

<body onload="initTheme()">
<div class="page">

    <h1>Web Checkers | ${title}</h1>

    <!-- Provide a navigation bar -->
    <#include "nav-bar.ftl" />

    <div class="body">

        <!-- Provide a message to the user, if supplied. -->
        <#include "message.ftl" />

        <!-- TODO: future content on the Home:
                to start games,
                spectating active games,
                or replay archived games
        -->

        <!-- List of Players below     -->
        <#include "player-lobby.ftl" />

        <#include "active-games.ftl" />

    </div>

</div>
</body>
<script data-main="/js" src="/js/initTheme.js"></script>
</html>
