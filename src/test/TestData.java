package test;

import data.*;

public class TestData {
    public static User[] users = new User[] {
            new User(1,"george.bluth@reqres.in","George","Bluth","https://reqres.in/img/faces/1-image.jpg"),
            new User(2,"janet.weaver@reqres.in","Janet","Weaver","https://reqres.in/img/faces/2-image.jpg"),
            new User(3,"emma.wong@reqres.in","Emma","Wong","https://reqres.in/img/faces/3-image.jpg"),
            new User(4,"eve.holt@reqres.in","Eve","Holt","https://reqres.in/img/faces/4-image.jpg"),
            new User(5,"charles.morris@reqres.in","Charles","Morris","https://reqres.in/img/faces/5-image.jpg"),
            new User(6,"tracey.ramos@reqres.in","Tracey","Ramos","https://reqres.in/img/faces/6-image.jpg"),
            new User(7,"michael.lawson@reqres.in","Michael","Lawson","https://reqres.in/img/faces/7-image.jpg"),
            new User(8,"lindsay.ferguson@reqres.in","Lindsay","Ferguson","https://reqres.in/img/faces/8-image.jpg"),
            new User(9,"tobias.funke@reqres.in","Tobias","Funke","https://reqres.in/img/faces/9-image.jpg"),
            new User(10,"byron.fields@reqres.in","Byron","Fields","https://reqres.in/img/faces/10-image.jpg"),
            new User(11,"george.edwards@reqres.in","George","Edwards","https://reqres.in/img/faces/11-image.jpg"),
            new User(12,"rachel.howell@reqres.in","Rachel","Howell","https://reqres.in/img/faces/12-image.jpg")
    };

    public static Users[] userPages = new Users[] {
            new Users(1, 6, 12, 2, new UserData[] {
                    new UserData(1,"george.bluth@reqres.in","George","Bluth","https://reqres.in/img/faces/1-image.jpg"),
                    new UserData(2,"janet.weaver@reqres.in","Janet","Weaver","https://reqres.in/img/faces/2-image.jpg"),
                    new UserData(3,"emma.wong@reqres.in","Emma","Wong","https://reqres.in/img/faces/3-image.jpg"),
                    new UserData(4,"eve.holt@reqres.in","Eve","Holt","https://reqres.in/img/faces/4-image.jpg"),
                    new UserData(5,"charles.morris@reqres.in","Charles","Morris","https://reqres.in/img/faces/5-image.jpg"),
                    new UserData(6,"tracey.ramos@reqres.in","Tracey","Ramos","https://reqres.in/img/faces/6-image.jpg"),
            }),
            new Users(2, 6, 12, 2, new UserData[] {
                    new UserData(7,"michael.lawson@reqres.in","Michael","Lawson","https://reqres.in/img/faces/7-image.jpg"),
                    new UserData(8,"lindsay.ferguson@reqres.in","Lindsay","Ferguson","https://reqres.in/img/faces/8-image.jpg"),
                    new UserData(9,"tobias.funke@reqres.in","Tobias","Funke","https://reqres.in/img/faces/9-image.jpg"),
                    new UserData(10,"byron.fields@reqres.in","Byron","Fields","https://reqres.in/img/faces/10-image.jpg"),
                    new UserData(11,"george.edwards@reqres.in","George","Edwards","https://reqres.in/img/faces/11-image.jpg"),
                    new UserData(12,"rachel.howell@reqres.in","Rachel","Howell","https://reqres.in/img/faces/12-image.jpg")
            }),
    };

    public static Resource[] resources = new Resource[] {
            new Resource(1,"cerulean",2000,"#98B2D1","15-4020"),
            new Resource(2,"fuchsia rose",2001,"#C74375","17-2031"),
            new Resource(3,"true red",2002,"#BF1932","19-1664"),
            new Resource(4,"aqua sky",2003,"#7BC4C4","14-4811"),
            new Resource(5,"tigerlily",2004,"#E2583E","17-1456"),
            new Resource(6,"blue turquoise",2005,"#53B0AE","15-5217"),
            new Resource(7,"sand dollar",2006,"#DECDBE","13-1106"),
            new Resource(8,"chili pepper",2007,"#9B1B30","19-1557"),
            new Resource(9,"blue iris",2008,"#5A5B9F","18-3943"),
            new Resource(10,"mimosa",2009,"#F0C05A","14-0848"),
            new Resource(11,"turquoise",2010,"#45B5AA","15-5519"),
            new Resource(12,"honeysuckle",2011,"#D94F70","18-2120"),
    };
    public static Resources[] resourcePages = new Resources[] {
            new Resources(1, 6, 12, 2, new ResourceData[] {
                    new ResourceData(1,"cerulean",2000,"#98B2D1","15-4020"),
                    new ResourceData(2,"fuchsia rose",2001,"#C74375","17-2031"),
                    new ResourceData(3,"true red",2002,"#BF1932","19-1664"),
                    new ResourceData(4,"aqua sky",2003,"#7BC4C4","14-4811"),
                    new ResourceData(5,"tigerlily",2004,"#E2583E","17-1456"),
                    new ResourceData(6,"blue turquoise",2005,"#53B0AE","15-5217"),
            }),
            new Resources(2, 6, 12, 2, new ResourceData[] {
                    new ResourceData(7,"sand dollar",2006,"#DECDBE","13-1106"),
                    new ResourceData(8,"chili pepper",2007,"#9B1B30","19-1557"),
                    new ResourceData(9,"blue iris",2008,"#5A5B9F","18-3943"),
                    new ResourceData(10,"mimosa",2009,"#F0C05A","14-0848"),
                    new ResourceData(11,"turquoise",2010,"#45B5AA","15-5519"),
                    new ResourceData(12,"honeysuckle",2011,"#D94F70","18-2120"),
            }),
    };
}
