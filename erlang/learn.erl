-module(learn).
-export([sum/1, createList/1, createReverseList/1]).
-export([filterLTE/2]).

sum(N) when N>0 -> N+sum(N-1);
sum(_) -> 0.

%% build list containing integers from 1 to N
createList(N) ->
    Create = fun(_Create, 1, List) -> 
        [1|List];
        (Create, H, List) ->
        Create(Create, H-1, [H|List])
    end,
    Create(Create, N, []).
     
createReverseList(N) ->
    Create = fun(_Create, Max, Max, List) -> 
        [Max|List];
        (Create, Max, Current, List) ->
        Create(Create, Max, Current+1, [Current|List])
    end,
    Create(Create, N, 1, []).
 
filterLTE([], _Threshold) ->
    [];
filterLTE([Head|List], Threshold) ->
   if
    Head =< Threshold -> [Head|filterLTE(List, Threshold)];
    true -> filterLTE(List, Threshold)
   end. 
    
