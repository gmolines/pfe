---
layout: page
title: GMD
longtitle: Génie Multimédia pour les Données massives
shortcut: true
permalink: /gmd/
---

{% assign sorted_posts = (site.posts | sort: 'title') %}

{% for post in sorted_posts %}
    {% if post.categories contains 'gmd' and post.categories contains 'dispo' %}
### {% if post.type == 'Research' %}[R]{% else %}[I]{% endif %} {{post.title}}    
  * Contact: {{post.contact}}
  * Identifiant projet: {{$post.pid}}
  * [Description complète du sujet]({{ site.baseurl }}{{ post.url }})
{{post.excerpt}}
    {% endif %}
{% endfor %}



