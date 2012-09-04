-module(reclearn).
% -include("reclearn_def.hrl").
-record(circle, {radius}).
-record(rectangle, {width, height}).
-export([square/1]).
%load records in shell: rr(reclearn).

square(#circle{radius=R}) ->
	3.14*R*R;
square(#rectangle{width=W, height=H}) ->
	W*H.
