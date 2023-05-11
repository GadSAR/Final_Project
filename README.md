# Final_Project
 
<iframe id="github-iframe" src=""></iframe>
<script>
    fetch('https://api.github.com/repos/ileathan/hubot-mubot/contents/src/mubot.coffee')
        .then(function(response) {
            return response.json();
        }).then(function(data) {
            var iframe = document.getElementById('github-iframe');
            iframe.src = 'data:text/html;base64,' + encodeURIComponent(data['content']);
        });
</script>
