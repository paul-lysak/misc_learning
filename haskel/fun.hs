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

myIsPalindrome = 

