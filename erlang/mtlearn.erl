-module(mtlearn).
-export([startEcho/0, printEcho/1, stopEcho/0, echoLoop/0]).

startEcho() ->
	register(echoProc, spawn(mtlearn, echoLoop, [])),
	io:format("hi all").
printEcho(Msg) ->
	echoProc!{self(), {print, Msg}},
	ok.
stopEcho() ->
	echoProc!{self(), {stop, ""}},
	ok.
echoLoop() ->
	receive
		{_Pid, {print, Message}} ->
			io:format("~s~n", [Message]),
			echoLoop();
		{_Pid, {stop, _Message}} ->
			io:format("gona stop")
	end.
