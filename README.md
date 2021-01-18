# CDR

## Summary
- Database Group Project
- Customized Drink Recommentder

### Problem Statement
1. Name: CDR(Customized Drink Recommender)
2. Problem Statement    
There is no system in the cafe drink ordering system that takes into
account the individual's illnesses such as allergies. A more effective
method is needed, rather than merely indicating the beverage material
small.

### E/R Diagram
![image](https://user-images.githubusercontent.com/28642467/104868968-181e5f00-5988-11eb-86f8-bf6873213bbb.png)

### Scenario
![image](https://user-images.githubusercontent.com/28642467/104869022-413eef80-5988-11eb-8f5a-daefbf0fc893.png)

<p align="center">Figure 1. Frow Overview with First Page of CDR</p>

#### Register(SignUp)
<img src = "https://user-images.githubusercontent.com/28642467/104869206-c924f980-5988-11eb-92fd-73d7f5a72664.png" width="30%" height="30%">
<p align="center">Figure 2. Register Page of CDR</p>

- Query
```
INSERT INTO userList VALUES(“ID", “Name", “Age", “Gender", “PW")
```

#### LogIn(SignIn)
  <img src = "https://user-images.githubusercontent.com/28642467/104869385-40f32400-5989-11eb-8c16-64cebcc26837.png" width="30%" height="30%">
  <p align="center">Figure 3. SignIn Page of CDR</p>
  <img src = "https://user-images.githubusercontent.com/28642467/104869491-831c6580-5989-11eb-9346-e20a5f97861d.png" width="30%" height="30%">
  <p align="center">Figure 4. Incorrect ID & PW</p>
    <img src = "https://user-images.githubusercontent.com/28642467/104869563-b3fc9a80-5989-11eb-85d6-9002cb64a326.png" width="30%" height="30%">
  <p align="center">Figure 5. Correct ID & PW</p>
  
- Query
  ```
  SELECT user_id, user_name, age, gender FROMuserList WHERE user_id=“ID" AND passwd=“PW"
  ```
  
#### My Page
 <img src = "https://user-images.githubusercontent.com/28642467/104869719-08077f00-598a-11eb-8145-66efd1ea5e3c.png" width="80%" height="80%">
  <p align="center">Figure 6. User My Page of CDR</p>
  
- Query
  - My Info
  - Picked List
  ```
  SELECT b.drink_id, a.drink_name FROM drink a INNER JOIN pickedList b ON a.drink_id=b.drink_id INNER JOIN userList c ON c.user_id=b.user_id WHERE (c.user_name= ‘name’)
  ```
  - Ordered List
  - Add Disease
  ```
  INSERT INTO sufferList(user_id, disease_id) VALUES( ‘ID’, ‘diseaseID’)
  ```
  - Check Disease
  ```
  SELECT disease_id, disease_name FROM diseasetable WHERE disease_id IN (SELECT disease_id FROM sufferList WHERE user_id=‘ID’)
  ```
  - Delete Disease
  ```
  DELETE FROM sufferList WHERE disease_id=‘diseaseID’ AND user_id=‘ID’
  ```
  - Change Password
  ```
  UPDATE userList SET passwd='newPW' WHERE user_id='ID'
  ```
  
#### Choose Drink
 <img src = "https://user-images.githubusercontent.com/28642467/104869997-b8758300-598a-11eb-8f8a-bf175a9fedc9.png" width="30%" height="30%">
  <p align="center">Figure 7. Choose Drink Page of CDR</p>
  
- Query
  - Cafe
  ```
  SELECT * FROM drink INNER JOIN drinkandcafe ON drink.drink_id = drinkandcafe.drink_id WHERE drinkanSELECT * FROM drink INNER JOIN drinkandcafe ON drink.drink_id = drinkandcafe.drink_id WHERE drinkandcafe.cafe_id = 1; 
  ```
  - New
  ```
  SELECT drink_name, price, temparature FROM drink WHERE release_date LIKE ‘%18/5%’ OR release_date LIKE ‘%18/4%’ OR release_date LIKE ‘%18/3%’ ;
  ```
  - Drink Recommand
  ```
  Next Page – Selection of Search Condition
  ```
 <img src = "https://user-images.githubusercontent.com/28642467/104870287-74cf4900-598b-11eb-91ab-b8b4ba3ae62c.png" width="30%" height="30%">
  <p align="center">Figure 8. Choose Custome Drink Page of CDR</p>
  
- Query
```
SELECT * FROM drink 
JOIN recipe ON recipe.drink_id = drink.drink_i JOIN ingredientslist
ON recipe.ingredient_name = ingredientslist.ingredient_name 
JOIN orderedList ON orderedList.drink_id = drink.drink_id
JOIN userList ON orderedList.user_id = userList.user_id
WHERE season = 'Winter' AND drink.price >=1500 AND drink.price
<= 5000 AND userList.gender = 'F' AND userList.age >= 50 AND
userList.age < 60 AND recipe.ingredient_name = 'Espresso'
GROUP BY orderedList.drink_id ORDER BY count DESC
```
#### Choose Option
- Choose Options using disease filter or not
<img src = "https://user-images.githubusercontent.com/28642467/104870448-e4453880-598b-11eb-83d4-dd72a13895ac.png" width="80%" height="80%">
<p align="center">Figure 9. Choose Options Page of CDR</p>

<img src = "https://user-images.githubusercontent.com/28642467/104870786-ab599380-598c-11eb-9796-da5b797dceea.png" width="30%" height="30%">
<p align="center">Figure 10. Choose Options Case 1 Page of CDR</p>

- Case 1) User choose options by their own preference
  - If disease filter is OFF
  ```
  SELECT choose_id FROM chooseList WHERE option1=‘caramelSyrup’ AND option2=’javaChip’ AND option3=’whippingCream’;
  ```
  - Else if disease filter if ON
  ```
  SELECT option_name FROM optionCauseList WHERE disease_id=1;
  ```
  ```
  SELECT * FROM optionList WHERE option_name <> ‘shot’ AND option_name <> ‘cinnamonSyrup’;
  ```
  ```
  CREATE OR REPLACE VIEW myDisease AS SELECT disease_id FROM sufferList WHERE user_id=1;
  ```

- Case 2) user choose options among the recommend List
<img src = "https://user-images.githubusercontent.com/28642467/104871389-41da8480-598e-11eb-9b62-bcada65c9389.png" width="80%" height="80%">
<p align="center">Figure 11. Choose Options Case 2(If disease filter is OFF) Page of CDR</p>

  - If disease filter is OFF,
  ```
  SELECT choose_id from (SELECT * FROM orderedList ORDER BY count DESC)t WHERE drink_id=4 LIMIT 5;
  ```
 <img src = "https://user-images.githubusercontent.com/28642467/104871636-ec52a780-598e-11eb-8eb2-e7899461e64f.png" width="80%" height="80%">
<p align="center">Figure 12. Choose Options Case 2(If disease filter is ON) Page of CDR</p>

  - Else if disease filter in ON
  ```
  CREATE OR REPLACE VIEW descOrderedListView AS SELECT * FROM
  orderedList ORDER BY count DESC;
  ```
  ```
  CREATE OR REPLACE VIEW tempChooseListView AS SELECT choose_id,
  option1, option2, option3, FROM chooseList WHERE choose_id=1 OR choose_id=5;
  ```
  ```
  SELECT choose_id FROM tempChooseListView WHERE option1<>’shot’ AND
  option1<>’cinnamonSyrup' AND option2<>'shot' AND option2<>'cinnamonSyrup'
  AND option3<>'shot' AND option3<>'cinnamonSyrup
  ```
  
  #### Order Drink
  <img src = "https://user-images.githubusercontent.com/28642467/104871675-0f7d5700-598f-11eb-88f5-a0890af4934d.png" width="40%" height="40%">
<p align="center">Figure 13. Choose Options Case 2(If disease filter is ON) Page of CDR</p>

- Query
```
CREATE OR REPLACE VIEW myDisease AS SELECT disease_id FROM sufferList WHERE user_id=‘ID’
```
```
SELECT SUM(IF("null" IN (option1), 0, 1))+SUM(IF("null" IN (option2), 0, 1))+SUM(IF("null" IN (option3), 0, 1)) AS SUM FROM chooseList WHERE choose_id=‘ChooseID’;
```
```
UPDATE orderedList SET count=‘count+1’ WHERE user_id=‘ID’ AND drink_id=‘DrinkID’ AND choose_id=‘ChooseID’;
```
