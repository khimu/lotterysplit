<%@ include file="/common/taglibs.jsp"%>

 <div class="post-info" style="background-color:white;padding:5px;-moz-border-radius: 15px;border-radius: 15px;">                            
 	<div class="post-basic-info">
         <h3>${game.gameName} Winning #'s</h3>
         <div class="page-nav">
            <div class="page-pigmintation top_page_nav" id="lotteryDivNumId${game.id}"></div></div>
            <div style="clear:both"></div>
            <script>
                $(document).ready(function(){       
                        var numbers = '${game.numbers}';
                        if(numbers != ''){
                                var array = numbers.trim().split(' ');
                                var lotteryNumbers = "<ul>";
                                for(var i = 0; i < array.length; i ++){
                                        lotteryNumbers += '<li><a href="#" style="color:black;background-image: url(/images/whiteball.jpeg);background-repeat:no-repeat;background-size:40px 40px;">'+array[i]+'</a></li>';
                                }
                                lotteryNumbers += "</ul>";   
                                $('#lotteryDivNumId${game.id}').html(lotteryNumbers);
                        }
                });
            </script>
         	<span><a alt="lottery winning number" title="lottery winning number" href="javascript:return false;">${game.prevDrawingDate}</a></span><br/>
         <div style="text-align:center;color:red;"><h3>${game.winningAmount}</h3></div><br/>
	</div>
</div>