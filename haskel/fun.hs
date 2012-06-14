import Data.List

add a b = a+b

lastButOne a = head (cutToLength2 a)

cutToLength2 a =  if null (tail (tail a))
	then a
	else cutToLength2 (tail a);

-- preLast :: [a] -> Maybe a
-- preLast (_:a:[])= Just a
-- preLast _ = Nothing 

data List a = Cons a (List a) | Nil deriving (Show)

fromList (x:xs) = Cons x (fromList xs)
fromList [] = Nil

toList (Cons a as) = a:toList as
toList Nil = []

myLength :: [a] -> Int 
myLength (_:xs) = 1 + myLength xs
myLength [] = 0 

myMean xs = (mySum xs) / fromIntegral (myLength xs)

mySum (x:xs) = x + mySum xs
mySum [] = 0


myPalindrome (x:xs) = [x] ++ myPalindrome(xs) ++ [x]
myPalindrome [] = []

myIsPalindrome [] = True
myIsPalindrome (x:[]) = True
myIsPalindrome (x:xs) = (x == z)  && (myIsPalindrome middle)
	where l = (length xs)
              middle = (take (l-1) xs)
              z = (head (drop (l-1) xs))

sortBySublists superList = sortBy compareListLength superList
	where compareListLength l1 l2 = compare (length l1) (length l2)

joinSublists sep [] = [];
joinSublists sep (subList:superList) = subList ++ sep ++ (joinSublists sep superList) 


data Tree a = Node a (Tree a) (Tree a)
	| Empty 
	deriving (Show)

sampleTree1 = (Node "root" (Node "left" Empty Empty) (Node "right" Empty Empty))
sampleTree2 = (Node "root" (Node "left" Empty (Node "left-right" Empty Empty)) (Node "right" Empty Empty))

treeDepth Empty = 0
treeDepth (Node a left right) = 1 + max (treeDepth left) (treeDepth right)


