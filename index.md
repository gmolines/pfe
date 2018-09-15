---
layout: page
title: Home
menu: false
---
## Propositions de projets 2018 - 2019

  * [Tous les sujets]({{BASE_PATH}} all-subjects) (ordre alphabétique)
  * [Tous les sujets]({{BASE_PATH}} pids) (Project ID)

#### Sujets disponibles par parcours

  * [AL: Architecture Logicielle]({{BASE_PATH}} al)
  * [CASPAR: CryptogrAphie, Sécurité, et vie Privée dans les Applications et Réseaux]({{BASE_PATH}} caspar)
  * [SD: Sciences des Données]({{BASE_PATH}} sd)
  * [IAM: Intelligence Ambiante]({{BASE_PATH}} iam)
  * [IHM: Interactions Homme-Machine]({{BASE_PATH}} ihm)
  * [WEB: Sciences, Technologies, Ressources et Applications du Web]({{BASE_PATH}} web)
  
#### Sujets attribués

{% for post in site.posts %}
    {% if post.categories contains 'oqp' %}
  * [{{$post.title}}]({{ BASE_PATH }}/pfe{{ post.url }}) ({{ $post.pid }})
    * Numéro de groupe: {{ $post.group }}
    * Encadrant:  {{ $post.contact }}
    * Étudiants: {%for stud in post.students %} *{{stud}}*,{%endfor %}
    {% endif %}
{% endfor %}

  
  
