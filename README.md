# ReadMe: Projet d'Injection de Dépendances

Ce projet illustre l'utilisation de l'injection de dépendances en Java, en utilisant différentes approches : instanciation statique, instanciation dynamique, et le framework Spring (avec des configurations XML et annotations). Voici un guide pour comprendre et exécuter le projet.

---

## **1. Créer l'interface `IDao` avec une méthode `getData`**

L'interface `IDao` définit une méthode `getData` qui retourne une valeur de type `double`.

```java
package Dao;

public interface IDao {
    double getData();
}
```

---

## **2. Créer une implémentation de cette interface**

Une implémentation concrète de l'interface `IDao` est fournie par la classe `DaoImpl`. Cette classe est annotée avec `@Component` pour être gérée par Spring.

```java
package Dao;

import org.springframework.stereotype.Component;

@Component("dao")
public class DaoImpl implements IDao {
    @Override
    public double getData() {
        System.out.println("Version base de données");
        return 23;
    }
}
```

---

## **3. Créer l'interface `IMetier` avec une méthode `calcul`**

L'interface `IMetier` définit une méthode `calcul` qui effectue un calcul basé sur les données fournies par `IDao`.

```java
package Metier;

public interface IMetier {
    double calcul();
}
```

---

## **4. Créer une implémentation de cette interface en utilisant le couplage faible**

La classe `MetierImpl` implémente l'interface `IMetier` et utilise l'interface `IDao` pour effectuer le calcul. Cela permet un couplage faible, car `MetierImpl` dépend de l'interface `IDao` et non d'une implémentation spécifique.

```java
package Metier;

import Dao.IDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("metier")
public class MetierImpl implements IMetier {
    private IDao dao;

    @Autowired
    public MetierImpl(@Qualifier("dao") IDao dao) {
        this.dao = dao;
    }

    @Override
    public double calcul() {
        return dao.getData() * 23;
    }

    public void setDao(IDao dao) {
        this.dao = dao;
    }
}
```

---

## **5. Faire l'injection des dépendances**

### **a. Par instanciation statique**

Dans cette approche, les dépendances sont injectées manuellement en utilisant le mot-clé `new`.

```java
package Presentation;

import Dao.DaoImpl;
import Metier.MetierImpl;

public class PresentationV1 {
    public static void main(String[] args) {
        // Injection des dépendances par instanciation statique
        DaoImpl dao = new DaoImpl();
        MetierImpl metier = new MetierImpl(dao); // Injection via le constructeur
        metier.setDao(dao); // Injection via le setter
        System.out.println("RES=" + metier.calcul());
    }
}
```

---

### **b. Par instanciation dynamique**

Dans cette approche, les dépendances sont injectées dynamiquement en utilisant la réflexion (reflection) pour charger les classes et invoquer les méthodes.

```java
package Presentation;

import Dao.IDao;
import Metier.IMetier;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Scanner;

public class PresentationV2 {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(new File("config.txt"));
            // Charger la classe Dao
            String daoClassname = scanner.nextLine();
            Class<?> cDao = Class.forName(daoClassname);
            IDao dao = (IDao) cDao.getConstructor().newInstance();
            // Charger la classe Metier
            String metierClassname = scanner.nextLine();
            Class<?> cMetier = Class.forName(metierClassname);
            IMetier metier = (IMetier) cMetier.getConstructor().newInstance();
            // Injecter la dépendance via le setter
            Method setDao = cMetier.getDeclaredMethod("setDao", IDao.class);
            setDao.invoke(metier, dao);
            System.out.println("RES=" + metier.calcul());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
```

Le fichier `config.txt` doit contenir les noms complets des classes à charger :

```
Dao.DaoImpl
Metier.MetierImpl
```

---

### **c. En utilisant le Framework Spring**

#### **Version XML**

Dans cette approche, la configuration des beans et des dépendances est définie dans un fichier XML (`config.xml`).

```xml
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="dao" class="Dao.DaoImpl" />
    <bean id="metier" class="Metier.MetierImpl">
        <constructor-arg ref="dao" />
    </bean>
</beans>
```

Le code pour charger la configuration Spring et exécuter l'application :

```java
package Presentation;

import Metier.IMetier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PresAvecSpringXML {
    public static void main(String[] args) {
        // Charger la configuration Spring depuis le fichier XML
        ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
        IMetier metier = context.getBean(IMetier.class);
        System.out.println("RES=" + metier.calcul());
    }
}
```

---

#### **Version Annotations**

Dans cette approche, les annotations Spring (`@Component`, `@Autowired`, `@Qualifier`) sont utilisées pour configurer les beans et les dépendances.

```java
package Presentation;

import Metier.IMetier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class PresSpringAnnotation {
    public static void main(String[] args) {
        // Charger la configuration Spring en utilisant les annotations
        ApplicationContext context = new AnnotationConfigApplicationContext("Dao", "Metier");
        IMetier metier = context.getBean(IMetier.class);
        System.out.println("RES=" + metier.calcul());
    }
}
```

---

## **6. Structure du Projet**

```
src/
├── Dao/
│   ├── IDao.java
│   └── DaoImpl.java
├── Metier/
│   ├── IMetier.java
│   └── MetierImpl.java
├── Presentation/
│   ├── PresentationV1.java
│   ├── PresentationV2.java
│   ├── PresAvecSpringXML.java
│   └── PresSpringAnnotation.java
├── config.xml
└── config.txt
```

---

## **7. Exécution**

1. **Instanciation Statique :** Exécutez `PresentationV1`.
2. **Instanciation Dynamique :** Exécutez `PresentationV2` après avoir configuré `config.txt`.
3. **Spring XML :** Exécutez `PresAvecSpringXML` après avoir configuré `config.xml`.
4. **Spring Annotations :** Exécutez `PresSpringAnnotation`.

---

Ce projet démontre comment l'injection de dépendances peut être réalisée de différentes manières, en fonction des besoins et des préférences de configuration.
