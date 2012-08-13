-module(learn).
-export([sum/1]).

sum(N) when N>0 -> N+sum(N-1);
sum(_) -> 0.


