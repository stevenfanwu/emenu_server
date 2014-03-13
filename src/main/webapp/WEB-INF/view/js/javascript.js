/* ************************************************
**************************************************

Website Name: Hummingbird System
Website URL: hummingbirdsystem.com
Website Author: Aaron Cheng
Author URL: chengfolio.com
Copyright 2014. All Rights Reserved.

************************************************ */

/* ***********************************************
*************************************************
0. General -------------------- All Files
1. Header js -------------------- All Files
2. Body js ---------------------- All Files
3. Home Page Styles ------------------- index.html
*************************************************
*********************************************** */


$(document).ready(function(){	
/*----------------------------------------------
------------------------------------------------
1. Home Page Styles
------------------------------------------------
----------------------------------------------*/	
	$(".btn-menu").on('tap', function(){
		if ($(".menu-nav").css("display") == "none") {
			$(".menu-nav").css("display","block");
		} else {
			$(".menu-nav").css("display","none");
		}
	});
/*----------------------------------------------
------------------------------------------------
3. Home Page Styles
------------------------------------------------
----------------------------------------------*/	
	//Resize&Reposition background img - direct from other pages
		var HeroHeight = $(".wrapper-hero img").height();
		var BackgroundHeight = Math.round(parseInt(HeroHeight  * 0.66));
		var BackgroundTop = Math.round(parseInt(HeroHeight  * 0.12));
		var SloganMargintop = Math.round(parseInt(HeroHeight  * 0.20));
		$(".background-hero").css("height",BackgroundHeight);
		$(".background-hero").css("top",BackgroundTop);
		$(".wrapper-hero .slogan").css("margin-top",SloganMargintop);
		
		$(window).resize(function(){
			var HeroHeight = $(".wrapper-hero img").height();
			var BackgroundHeight = Math.round(parseInt(HeroHeight  * 0.66));
			var BackgroundTop = Math.round(parseInt(HeroHeight  * 0.12));
			var SloganMargintop = Math.round(parseInt(HeroHeight  * 0.20));
			$(".background-hero").css("height",BackgroundHeight);
			$(".background-hero").css("top",BackgroundTop);
			$(".wrapper-hero .slogan").css("margin-top",SloganMargintop);
		});
		
	//Resize&Reposition background img - first time load
	$(".wrapper-hero img").load(function() {
		var HeroHeight = $(".wrapper-hero img").height();
		var BackgroundHeight = Math.round(parseInt(HeroHeight  * 0.66));
		var BackgroundTop = Math.round(parseInt(HeroHeight  * 0.12));
		var SloganMargintop = Math.round(parseInt(HeroHeight  * 0.20));
		$(".background-hero").css("height",BackgroundHeight);
		$(".background-hero").css("top",BackgroundTop);
		$(".wrapper-hero .slogan").css("margin-top",SloganMargintop);
		
		$(window).resize(function(){
			var HeroHeight = $(".wrapper-hero img").height();
			var BackgroundHeight = Math.round(parseInt(HeroHeight  * 0.66));
			var BackgroundTop = Math.round(parseInt(HeroHeight  * 0.12));
			var SloganMargintop = Math.round(parseInt(HeroHeight  * 0.20));
			$(".background-hero").css("height",BackgroundHeight);
			$(".background-hero").css("top",BackgroundTop);
			$(".wrapper-hero .slogan").css("margin-top",SloganMargintop);
		});
    });
	
	

});