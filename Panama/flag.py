__author__ = 'Michael Kochell'

from graphics import *
from math import sin, cos, pi
import math

wintitle = 'Panama Flag'
winheight = 600
winwidth = 1000

win = GraphWin(wintitle, winwidth, winheight)

def getdistance(x, y, angle, length):
    xdis = cos(angle - 0.5 * pi) * length
    ydis = sin(angle - 0.5 * pi) * length

    return xdis + x, ydis + y

def getthird(p1, p2, angle, distance):
    xdis = p2.getX() - p1.getX()
    ydis = p2.getY() - p1.getY()
    middlex = 0.5 * xdis + p1.getX()
    middley = 0.5 * ydis + p1.getY()
    x = middlex + (cos(angle - 0.5 * pi) * distance)
    y = middley + (sin(angle - 0.5 * pi) * distance)
    return Point(x, y)

def drawstar(center, color, win):
    angles1 = [0.1, 0.3, 0.5, 0.7, 0.9]
    points = []
    for angle in angles1:
        x, y = getdistance(center.getX(), center.getY(), angle * 2 * pi, winwidth / 30)
        points.append(Point(x, y))
    poly = Polygon(points)
    poly.setFill(color)
    poly.setOutline(color)
    poly.draw(win)

    angles2 = [0, 0.2, 0.4, 0.6, 0.8]
    for i in range(-1, 4):
        p1 = points[i]
        p2 = points[i + 1]
        p3 = getthird(p1, p2, angles2[i + 1] * 2 * pi, winwidth / 15)
        tri = Polygon([p1, p2, p3])
        tri.setFill(color)
        tri.setOutline(color)
        tri.draw(win)

def drawrecs():
    color = 'red'
    p1 = Point(0.5 * winwidth, 0)
    p2 = Point(winwidth, 0.5 * winheight)
    rec = Rectangle(p1, p2)
    rec.setFill(color)
    rec.setOutline(color)
    rec.draw(win)

    p1 = Point(0, 0.5 * winheight)
    p2 = Point(0.5 * winwidth, winheight)
    rec = Rectangle(p1, p2)
    rec.setFill('blue')
    rec.setOutline('blue')
    rec.draw(win)

if __name__ == '__main__':
    drawrecs()
    bluecenter = Point(0.25 * winwidth, 0.25 * winheight)
    drawstar(bluecenter, 'blue', win)
    redcenter = Point(0.75 * winwidth, 0.75 * winheight)
    drawstar(redcenter, 'red', win)
    win.getMouse()
    win.close()