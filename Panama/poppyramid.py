__author__ = 'Michael Kochell'

from graphics import *
wintitle = 'Population Pyramid'
winheight = 600
winwidth = 800

win = GraphWin(wintitle, winwidth, winheight)

def drawtexttowin(text, x, y, size, win):
    p = Point(x, y)
    label = Text(p, text)
    label.setSize(size)
    label.draw(win)

def drawoutline():

    drawtexttowin('Panama - 2014', (0.5 * winwidth) - 5, 20, 12, win)
    drawtexttowin('Male', 20, 20, 10, win)
    drawtexttowin('Female', winwidth - 25, 20, 10, win)
    drawtexttowin('Population (in thousands)', 80, winheight - 50, 10, win)
    drawtexttowin('Population (in thousands)', winwidth - 80, winheight - 50, 10, win)
    drawtexttowin('Age Group', (0.5 * winwidth) - 4, winheight - 50, 10, win)

    drawtexttowin('100+', (0.5 * winwidth - 4), 50, 10, win)

    for i in range(18):
        startnum = 10 + i * 5
        endnum = 14 + i * 5
        labeltext = '{0} - {1}'.format(str(startnum), str(endnum))
        drawtexttowin(labeltext, (0.5 * winwidth) - 3, winheight - 190 - (i * 20), 10, win)

    drawtexttowin('5 - 9', (0.5 * winwidth) - 3, winheight - 170, 10, win)
    drawtexttowin('0 - 4', (0.5 * winwidth) - 3, winheight - 150, 10, win)

    bottomlabels = [175, 140, 105, 70, 35]
    for i in range(5):
        drawtexttowin(str(bottomlabels[i]), i * 69 + 19, winheight - 100, 10, win)

    for i in range(1, 6):
        drawtexttowin(str(bottomlabels[-i]), (0.5 * winwidth) + i * 69 + 29, winheight - 100, 10, win)

    drawtexttowin('0', (0.5 * winwidth) - 43, winheight - 100, 10, win)

    drawtexttowin('0',(0.5 * winwidth) + 42, winheight - 100, 10, win)

    for i in range(6):
        p1 = Point(20 + i * 68, winheight - 140)
        p2 = Point(20 + i * 68, winheight - 115)
        line = Line(p1, p2)
        line.draw(win)

    for i in range(6):
        p1 = Point((0.5 * winwidth) + 40 + i * 68, winheight - 140)
        p2 = Point((0.5 * winwidth) + 40 + i * 68, winheight - 115)
        line = Line(p1, p2)
        line.draw(win)

    rec1 = Rectangle(Point(20, 40), Point((0.5 * winwidth) - 40, winheight - 140))
    rec1.draw(win)
    rec2 = Rectangle(Point((0.5 * winwidth) + 40, 40), Point(winwidth - 20, winheight - 140))
    rec2.draw(win)

def drawgraph():
    malecolors = ['dark blue', 'blue']
    malevalues = [168, 171, 162, 162, 152, 146, 134, 129, 123, 109, 90, 72, 61, 50, 36, 25, 16, 7, 4, 1, 1]
    for i in range(21):
        p1 = Point((0.5 * winwidth) - 40 - malevalues[i] * 1.95, winheight - 142 - (i * 20))
        p2 = Point((0.5 * winwidth) - 40, winheight - 160 - (i * 20))
        rec = Rectangle(p1, p2)
        rec.setFill(malecolors[i % 2])
        rec.draw(win)

    femalecolors = ['red', 'pink']
    femalevalues = [162, 164, 156, 156, 146, 138, 129, 126, 118, 107, 90, 72, 61, 50, 39, 29, 20, 12, 5, 2, 1]
    for i in range(21):
        p1 = Point((0.5 * winwidth) + 40 + femalevalues[i] * 1.95, winheight - 142 - (i * 20))
        p2 = Point((0.5 * winwidth) + 40, winheight - 160 - (i * 20))
        rec = Rectangle(p1, p2)
        rec.setFill(femalecolors[i % 2])
        rec.draw(win)

if __name__ == '__main__':
    drawoutline()
    drawgraph()
    win.getMouse()
    win.close()