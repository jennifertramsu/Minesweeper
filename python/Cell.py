class Cell:
        
    def __init__(self, x: int, y: int, cellType):
        self.x = x;
        self.y = y;
        self.Type = cellType;        
        self.uncovered = False;
        self.number = None;
    
    # SETTERS
    
    def uncover(self):
        self.uncovered = True;
       
    def setNumber(self, n: int):
        self.number = n;
        
    # PRINT
    
    def __repr__(self):
        return str.format("Int x = {}, int y = {}, Type: {}", self.x, self.y, self.Type);
        