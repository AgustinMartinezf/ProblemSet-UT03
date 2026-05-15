# Ejercicio 5
Describir en lenguaje natural, pre y post condiciones, seudocódigo de las operaciones y análisis
de ordenes de las siguientes operaciones del Trie y NodoTrie:
## 1. Operación buscar palabra completa.
### Lenguaje natural
Se recorre el Trie caracter por caracter según la conformacion de la palabra. Si algún caractér de la palabra no existe entonces la palabra no está en el trie. Si el recorrido termina y la palabra se formó correctamente entonces la palabra si existe.
### Precondiciones:
El trie ecxiste y es válido y la palabra no es null.
### Postcondiciones:
Devuelve una Entry<T> asociada si la palabra existe y null si no existe. No modifica el trie original.
### Pseudocódigo:
-Trie: 
-  func buscar(palabra):
-    if raiz = null: //si esta vacío no busco nda
-        return null
-    end if
-   return raiz.buscar(palabra)
-   end buscar

NodoTrie:
-  func buscar(palabra)
-    nodoActual ← this
-    for i ← 0 to palabra.length - 1 do //recorremos letra por letra
-        caracter ← palabra[i] //letra actual
-        indice ← caracter - 'a' //convierto la letra en índice
-        if nodoActual.hijos[indice]= null then //si el hijo no existe la palabra no está
-            return null
-        end if
-        nodoActual ←nodoActual.hijos[indice] //avanzamos
-    end for
-    if nodoActual.esPalabra then //si representa una palabra
-        return nueva Entry(palabra,nodoActual.dato) //la devuelvo con dato asociado
-    end if
-    return null
-end buscar
### Complejidad:
o(m) siendo m la longitud de la palabra.  

## 2. Obtener la lista de palabras por un prefijo dado.
### Lenguaje natural: 
Se busca el nodo que corresponde al último caracter del prefijo y luego se recorren todos los subárboles (descencientes) para poder obtener todas las palabras que arrancan con ese prefijo dado.
### Precondiciones:
El trie existe y es válido, el prefijo no es null.
### Postcondiciones:
Retorna una lista con las palabras que comienzan con ese prefijo y el trie se mantiene igual, no se modifica.
### Pseudocódigo:
Trie:
func listarPrefijo(prefijo)
    if raiz = null then //si esta vacío no hay palabras 
        RETURN lista vacía
    end if
    returnraiz.predecir(prefijo)
end listarPrefijo

NodoTrie:
func listarPrefijo(prefijo)
    lista ← nueva lista
    nodoActual ← this
    for i ← 0 to prefijo.length - 1 do //recorremos el prefijo letra por letra
        caracter ← prefijo[i]
        indice ← caracter - 'a' //para convertirlo a índice
        if nodoActual.hijos[indice]= null then //si no existe el hijo el prefijo no está
            return lista
        end if
        nodoActual ←nodoActual.hijos[indice] //avanzamos
    end for
    nodoActual.obtenerPalabras(prefijo,lista) //hice una función auxiliar que recorre los descendientes
    return lista
end listarPrefijo

Auxiliar:
proc obtenerPalabras(palabraActual,lista)
    if esPalabra then //si representa palabra
        lista.agregar(nueva Entry(palabraActual,dato)) //agrego
    end if
    for i ← 0 to 25 do //recorro todos los hijos
        if hijos[i] !=null then //si existe
            letra ← ('a' + i)
            hijos[i].obtenerPalabras(palabraActual + letra,lista) //seguimos recorriendo
        end if
    end for
end proc

### Complejidad:
o(m+n) donde m es la longitud del prefijo mientras que n es la cantidad de nodos recorridos.

## 3. Insertar una palabra con un dato asociado.
### Lenguaje natural:
Se recorre el trie caracter por caracter. En el caso de que algun nodo no exista, se crea. Al llegar al último nodo se marca la palabra como válida (el nodo final también) y se guarda el dato asociado.
### Precondiciones: 
El trie debe ser valido y existir, la palabra no debe de ser null.
### Postcondiciones: 
La palabra se agrega al trie, el dato asociado se guarda, retorna true si insertó y false si no.
### Pseudocódigo:
Trie:
func insertar(palabra, dato)
    if raiz = null then //si esta vacío creamos la raiz nueva
        crear nueva raíz
    end if
    return raiz.insertar(palabra,dato) //sino insertamos
end func

NodoTrie:
func insertar(palabra, dato)
    nodoActual ← this
    for i ← 0 to palabra.length - 1 do //recorremos la palabra letra por letra
        caracter ← palabra[i]
        indice ← caracter - 'a' //convertimos a índice
        if nodoActual.hijos[indice]= null then //si el hijo no existe lo creamos
            nodoActual.hijos[indice]← nuevo NodoTrie
        end if
        nodoActual ←nodoActual.hijos[indice] //avanzamos
    end for
    if nodoActual.esPalabra then //si ya era completa no insertamos nada
        return false
    end if
    nodoActual.esPalabra ← true //marcamos el final como válida
    nodoActual.dato ← dato
    return true
end insertar
### Complejidad: 
o(m) siendo m la longitud de la palabra

## 4. Eliminar una palabra del Trie.
### Lenguaje natural: 
Se eliminan de manera recursiva los nodos que ya no pertenezcan a ninguna otra palabra del trie.
### Precondiciones: 
Trie existente y válido, palabra diferente de null.
### Postcondiciones:
La palabra deja de existir en el trie, no se eliminan nodos que pertenezcan a otras palabras.
### Pseudocódigo:
Trie:
func eliminar(palabra)
    if raiz = null then //si esta vacío no elimino nada
        RETURN false
    end if
return raiz.eliminar(palabra,0)
end eliminar

NodoTrie:
func eliminar(palabra, indice)
    if indice =palabra.length then //nuestro caso base es si llegamos al final de la palabra
        if !esPalabra then //si no representa palabra no existe
            return false
        end if
        esPalabra ← false //desmarcamos nodo como palabra válida
        return noTieneHijos() //si no tiene hijos puede borrarse, sino no puede borrarse
    end if
    caracter ← palabra[indice]
    posicion ← caracter - 'a'
    hijo ← hijos[posicion]
    if hijo = null then
        return false
    end if
    borrarHijo ←hijo.eliminar(palabra,indice + 1) //eliminamos recursivamente
    if borrarHijo then 
        hijos[posicion] ← null
        RETURN !esPalabra && noTieneHijos() //devuelve true solo si el nodo no representa palabra y no tiene hijos.
    end if
    return false
end eliminar

### Complejidad: o(m) siendo m la longitud de la palabra.
