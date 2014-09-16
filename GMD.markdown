---
layout: page
title: GMD
longtitle: Génie Multimédia pour les Données massives
shortcut: true
permalink: /gmd/
---


{% for post in site.posts %}
    {% if post.categories contains 'gmd' and post.categories contains 'dispo' %}
### {% if post.type == 'Research' %}[R]{% else %}[I]{% endif %} {{post.title}}    
  * Contact: {{post.contact}}
  * Identifiant projet: {{$post.pid}}
  * [Description complète du sujet]({{ site.baseurl }}{{ post.url }})
{{post.excerpt}}
    {% endif %}
{% endfor %}



