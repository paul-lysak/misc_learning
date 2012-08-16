-module(learn).
-export([sum/1, createList/1, createReverseList/1]).
-export([filterLTE/2]).
-export([concatLists/1, reverse/1, flatten/1]).
-export([concatTwo/2]).

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

%% filter out elements bigger then threshold
filterLTE([], _Threshold) ->
    [];
filterLTE([Head|List], Threshold) ->
   if
    Head =< Threshold -> [Head|filterLTE(List, Threshold)];
    true -> filterLTE(List, Threshold)
   end. 
    
%% concatenate list of lists into list of items 
concatLists([]) ->
	[];
concatLists([Items|ListOfLists]) when is_list(Items) ->
	Items++concatLists(ListOfLists);
concatLists(NotAList) ->
	NotAList.

reverse(List)->
	reverseAcc([], List).

reverseAcc(Acc, []) ->
	Acc;
reverseAcc(Acc, [Head|Tail]) ->
	reverseAcc([Head|Acc], Tail).

concatTwo([], List2) ->
	List2;
concatTwo([H1|Tail1], List2)->
	[H1| concatTwo(Tail1, List2)].

%% flatten nested lists 
flatten([]) ->
	[];
flatten([Head|Tail]) when is_list(Head) ->
	flatten(concatLists(Head) ++ Tail);
flatten([Head|Tail]) ->
	[Head |flatten(Tail)].

