import random as rand

wordlist = ['ability','random','glass','easy','gym','canon','book','trivial','earphones','keyboard']

class Game():
    def __init__(self, player_count):
        self.player_count = player_count
    
    def _play(self):
        print()
        print("The game started with {} players".format(self.player_count))

class Hangman(Game):
    def __init__(self, player_count, chances):
        super().__init__(player_count)
        self.chances = chances
        self.players = []
        if player_count == 1:
            self.players.append(Player(1,rand.choice(wordlist),chances))
        else: 
            mpwordlist = []
            player_number = 1
            print('Enter one word for each player')
            while player_number <= player_count:
                letters = input("Player {}: ".format(player_number)).lower()
                if not letters.isalpha():
                    print("Only letters are allowed!")
                else:
                    mpwordlist.append(letters)
                    player_number = player_number + 1
            self.players.append(Player(1,mpwordlist[1],chances))
            self.players.append(Player(2,mpwordlist[0],chances))


    def __reveal_word(self, player):
        word = ''
        for letter in player.word: word += ('_', letter)[letter in player.strike]
        return word

    def _play(self):
        super()._play()
        end_game = False
        round = 1
        while True:
            if not self.players:
                print("Game over!")
                break
            if len(self.players) == 2: print("Round {}".format(round))
            else: print("")
            for player in self.players:
                if len(self.players) == 2: print("Player {} turn".format(player.id))
                print("")
                print(self.__reveal_word(player))
                input_letter = input("Player {} ({} chances): ".format(player.id,player.chance))
                if not input_letter.isalpha():
                    print("Only letters are allowed!")
                    player.chance = player.chance - 1
                elif(input_letter in player.strike):
                    print("You already guessed this letter!")
                    player.chance = player.chance - 1
                elif(len(input_letter) != 1):
                    print("You can guess only one letter!")
                    player.chance = player.chance - 1
                elif(input_letter in player.word):
                    print("You guessed the letter {}!".format(input_letter))
                    player.strike.append(input_letter)
                else: 
                    print("You guessed the wrong letter!")
                    player.chance = player.chance - 1

                if player.chance == 0:
                    print("Sorry Player {}, you lost all of your chances!".format(player.id))
                    print("Your word was: {}".format(player.word))
                    self.players.remove(player)
                elif player.word == str(self.__reveal_word(player)):
                    print("Player {} won the game!".format(player.id))
                    end_game = True
                    break
            if end_game:
                break
            round = round + 1


class Player():
    def __init__(self,id,word,chance):
        self.id = id
        self.word = word
        self.chance = chance
        self.strike = []

def start():
    print("Choose your Game mode")
    print("1.Singleplayer")
    print("2.Multiplayer (up to 2 players)")
    print("3.Exit")
    while True:
        try:
            answer=int(input("-> "))
            if answer in range(1,3): break
            else: print("That's not a option!")
        except: print("That's not a valid option!")
    if answer == 1:
        print("You chose Singleplayer Mode")
        choose_dificulty(1)
    elif answer == 2:
        print("You chose Multiplayer Mode")
        choose_dificulty(2)
    elif answer == 3:
        print("Goodbye!")

def choose_dificulty(mode):
    print("Choose your Game mode")
    print("1.Beginner (8 chances)")
    print("2.Intermediate (5 chances)")
    print("3.Advanced (3 chances)")
    print("4.Return")
    print("5.Exit")
    while True:
        try:
            answer=int(input("-> "))
            if answer in range(1,5): break
            else: print("That's not a option!")
        except: print("That's not a valid option!")
    if answer == 1:
        print("You chose Beginner Difficulty")
        Hangman(mode, 8)._play()
    elif answer == 2:
        print("You chose Intermediate Difficulty")
        Hangman(mode, 5)._play()
    elif answer == 3:
        print("You chose Advanced Difficulty")
        Hangman(mode, 3)._play()
    elif answer == 4:
        start()
    elif answer == 5:
        print("Goodbye!")


print("Welcome to Hangman!")
start()