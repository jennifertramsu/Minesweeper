import random
from Cell import Cell

class Board:
    
    def __init__(self, size: int):
        self.board = [[Cell(i, j, "BLANK") for j in range(size)] for i in range(size)];
        self.mines = size*size // 8;
        self.gameDone = False;
        self.blanks = 0;
        self.blankCount = 0;
        
        # fill board up with mines
        
        random.seed(1);
        
        for i in range(self.mines):
            x = random.randint(0, size - 1);
            y = random.randint(0, size - 1);
            
            if self.board[x][y].Type == "MINE": # avoid repeats
                i -= 1;
                continue;
            else:
                self.board[x][y].Type = "MINE";
                
        # set numbers for non-MINEs
        
        for i in range(size):
            for j in range(size):
                if self.board[i][j].Type != "MINE":
                    self.board[i][j].number = self.checkMines(self.board[i][j]);
                    if self.board[i][j].Type == "BLANK":
                        self.blanks += 1
        
    def checkMines(self, cell: Cell):
        mineCount = 0;
        x = cell.x;
        y = cell.y;
        
        otherX = x - 1; # starting in top-left corner
        otherY = y - 1;

        for i in range(3):
            for j in range(3):
                if otherX + i == x and otherY + j == y:
                    continue
                if otherX + i < 0:
                    continue;
                if otherY + j < 0:
                    continue;
                if otherX + i > len(self.board) - 1:
                    continue;
                if otherY + j > len(self.board) - 1:
                    continue;
                if self.board[otherX + i][otherY + j].Type == "MINE":
                    mineCount += 1;
        
        if mineCount == 0:
            cell.Type = "BLANK";
        else:
            cell.Type = "NUMBER";
        
        return mineCount;
    
    def play(self, x: int, y: int):
                
        if self.gameDone:
            return

        if x < 0 or y < 0 or x > len(self.board)-1 or y > len(self.board)-1:
            return

        self.checkEnd()

        cell = self.board[x][y];

        if cell.uncovered == True:
            return

        if cell.Type == "MINE":
            self.gameDone = True;
            print("You found a mine!");
        elif cell.Type == "NUMBER":
            cell.uncover();
        elif cell.Type == "BLANK":
            cell.uncover();   
            self.blankCount += 1
            self.play(x, y+1)
            self.play(x, y-1)
            self.play(x-1, y)
            self.play(x+1, y)
            self.play(x-1, y-1)
            self.play(x-1, y+1)
            self.play(x+1, y-1)
            self.play(x+1, y+1)   
            
    def checkEnd(self):
        if self.blanks == self.blankCount:
            self.gameDone = True
            print("Field cleared!\n")
        