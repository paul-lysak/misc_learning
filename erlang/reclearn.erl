-module(reclearn).
-include("reclearn_def.hrl").
-export([square/1]).

square(#circle{radius=R}) ->
	3.14*R*R.

