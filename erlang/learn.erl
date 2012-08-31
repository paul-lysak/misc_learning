-module(learn).
-export([sum/1, createList/1, createReverseList/1]).
-export([filterLTE/2]).
-export([concatLists/1, reverse/1, flatten/1]).
-export([concatTwo/2]).
-export([mergeSort/1, mergeLists/3]).
-export([quickSort/1, splitByValue/4]). 
-export([prepareIndex/1]).

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

mergeSort([]) ->
	[];
mergeSort([Elm]) ->
	[Elm];
mergeSort([Elm1, Elm2]) when Elm1 > Elm2 ->
	[Elm2, Elm1];
mergeSort([Elm1, Elm2]) ->
	[Elm1, Elm2];
mergeSort(List) ->
%TODO avoid using list length
	Parts = lists:split(length(List) div 2, List),
	Part1 = mergeSort(element(1, Parts)),
	Part2 = mergeSort(element(2, Parts)),
	mergeLists([], Part1, Part2).
mergeLists(L, [H1|T1], [H2|T2]) when H1<H2 ->
	mergeLists(L++[H1], T1, [H2|T2]);
mergeLists(L, [H1|T1], [H2|T2]) ->
	mergeLists(L++[H2], [H1|T1], T2);
mergeLists(L, L1, []) ->
	L ++ L1;
mergeLists(L, [], L2) ->
	L ++ L2.


quickSort([]) -> 
	[];
quickSort([E]) ->
	[E];
quickSort([H|T]) ->
	Parts = splitByValue(H, [], [], T),
	quickSort(element(1, Parts)) ++ [H] ++ quickSort(element(2, Parts)).
splitByValue(V, Less, More, [H|L]) ->
	if H<V ->
		splitByValue(V, [H|Less], More, L);
	true ->
		splitByValue(V, Less, [H|More], L)
	end;
splitByValue(_V, Less, More, []) ->
	{Less, More}.

prepareIndex([]) ->
	[];
prepareIndex([H|T]) ->
	prepareIndex([], {H,H}, T).
prepareIndex(Collected, Pending, []) ->
	Collected ++ [Pending];
prepareIndex(Collected, Pending, [H|T]) ->
	Prev = element(2, Pending), 
	if
		(Prev == H-1) or (Prev == H) -> prepareIndex(Collected, {element(1, Pending), H}, T);
		true -> prepareIndex(Collected ++ [Pending], {H, H}, T)
	end.
