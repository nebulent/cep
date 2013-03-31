<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="${base}/css/ui-lightness/jquery.ui.theme.css" rel="stylesheet" type="text/css" />
<link href="${base}/css/ui-lightness/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="${base}/css/styles.css" rel="stylesheet" type="text/css" />
<link href="${base}/css/jquery.dataTables.css" rel="stylesheet" type="text/css" />
<link href="${base}/bootstrap/css/bootstrap.min.css" rel="stylesheet" media="screen">
<style>
      body {
        padding-top: 60px; /* 60px to make the container go all the way to the bottom of the topbar */
      }
</style>

<script src="${base}/js/jquery-1.9.0.js" type="text/javascript" language="javascript"></script>
<script src="${base}/js/jquery-ui-1.10.0.custom.min.js" type="text/javascript" language="javascript"></script>
<script src="${base}/js/jquery.tmpl.min.js" type="text/javascript" language="javascript"></script>
<script src="${base}/js/jquery.tmplPlus.min.js" type="text/javascript" language="javascript"></script>
<script src="${base}/js/jquery.dataTables.min.js" type="text/javascript" language="javascript"></script>
<script src="${base}/bootstrap/js/bootstrap.min.js"></script>

${head}
<title>${title}</title>
</head>

<body>
    <div class="navbar navbar-inverse navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container">
          <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="brand" href="#">CEP</a>
          <div class="nav-collapse collapse">
            <ul class="nav">
              <li class="active"><a href="${base}/dashboard">Dashboard</a></li>
              <li><a href="${base}/monitor">Monitors</a></li>
              <li><a href="${base}/alert">Alerts</a></li>
            </ul>
          </div><!--/.nav-collapse -->
        </div>
      </div>
    </div>

    <div class="container">
    ${body}    
	</div>

</body>
</html>
