from Board import Board

def displayBoard(field: Board):
    print("Blanks Left: " + str(field.blanks - field.blankCount) + "\n")
    # HEADER
    print("       ", end="");
    for i in range(len(field.board)):
        if i >= 10:
            print(i, end="   ");
        else:
            print(i, end="    ");
    print("\n");
    
    # SIDE NUMBER, BOARD
    
    for i in range(len(field.board)):
        if i >= 10:
            print(i, end="");
            print(" ", end="");
        else:
            print(i, end="");
            print("  ", end="");
        
        for j in range(len(field.board[i])):
            print(" || ", end="");
            if not field.board[i][j].uncovered:
                print("-", end="");
            else:
                if field.board[i][j].Type == "MINE":
                    print("M", end="");
                elif field.board[i][j].Type == "BLANK":
                    print(" ", end="");
                else:
                    print(field.board[i][j].number, end="");
        print(" ||");
    print("\n")

def displayAll(field: Board):
    for i in range(len(field.board)):
        for j in range(len(field.board[i])):
            field.board[i][j].uncover();
    
    displayBoard(field);    
    
# MAIN PROGRAM
while 1:
    size = int(input("Welcome! How large would you like the field to be? "))
    if size > 3:
        break
    else:
        print("Dimension cannot be smaller than 3!")
game = Board(size);
    
while not game.gameDone:
    displayBoard(game);
    
    try:
        x = int(input("X Coordinate: "));
        print("\n")
        y = int(input("Y Coordinate: "));
        print("\n")
        
        if x > len(game.board) - 1:
            print("Out of range!");
            continue;
        
        if y > len(game.board[x]) - 1:
            print("Out of range!");
            continue;
    except:
            print("Invalid input!");
            continue;
    
    game.play(x, y);

displayAll(game);