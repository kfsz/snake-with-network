#!/usr/bin/python3

import socket
import socketserver
import threading
import pickle
import os

# for locking highscore list, while adding a new record
lock = threading.Lock()


score = []
savefile = "highscore.txt"

class TCPHandler(socketserver.BaseRequestHandler):

    # communication with client
    def handle(self):
        # self.request is the TCP socket connected to the client
        # that gets data now - straight string from client
        self.data = self.request.recv(1024).strip()      
        print("{} wrote:".format(self.client_address[0]))
        #print(self.data.decode("utf-8"))
        #print("END MESSAGE")       
        ###self.request.sendall(b'congrats')
        
        # split the string
        currentscore = self.data.decode("utf-8").split("\n")
        currentscore[0] = int(currentscore[0])
        print(currentscore)      
        
        with lock:
            #print("locktest")
            global score
            score += [currentscore]
        #print(score)    
            
        score.sort(key=lambda x:int(x[0]), reverse=True)
                
        # experimental highscore table
        scoreNew = score
        for item in scoreNew:
            item[0] = str(item[0])
        table = ""    
        for item in scoreNew:
            table += ' '.join(item)
            table += ":"       
        #print(table)   
        #self.request.sendall(bytes(sys.getsizeof(table)))
        self.request.sendall(table.encode())
        self.dataok = self.request.recv(1024).strip()      
        #print(self.dataok)
        
        # can add diffrent pics for diffrent places
        if True: #currentscore[0] == score[0][0]:     
            # client doesn't mind if not sent anything        
            with open("pic.bmp", 'rb') as fs: 
               picdata = fs.read(1024)
               while picdata:
                    self.request.sendall(picdata)
                    picdata = fs.read(1024) 
        
def exitcondi():
    a = input("running server / input anything to quit\n")
    server.shutdown()
    
    # here goes saving
    global score
    with open(savefile, 'wb') as fs: 
        pickle.dump(score, fs)
    print("finish!")
    
def loaddata():

    # load into that struct
    global score
    if os.path.isfile(savefile):    
        with open(savefile, 'rb') as fs: 
            score = pickle.load(fs)
            
    print("current score at load")
    print(score) 
    
if __name__ == "__main__":
    HOST, PORT = "localhost", 50007

    # load data
    loaddata()
    
    # for nice quiting
    p = threading.Thread(target=exitcondi)
    p.start()
    
    # server init
    server = socketserver.TCPServer((HOST, PORT), TCPHandler)
    server.serve_forever()

