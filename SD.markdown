---
layout: page
title: SD
longtitle: Sciences des Données
shortcut: true
permalink: /sd/
---

{% assign sorted_posts = (site.posts | sort: 'title') %}

{% for post in sorted_posts %}
    {% if post.categories contains 'sd' and post.categories contains 'dispo' %}
### {{post.title}}    
  * Contact: {{post.contact}}
  * Identifiant projet: {{$post.pid}}
  * [Description complète du sujet]({{ site.baseurl }}{{ post.url }})
{{post.excerpt}}
    {% endif %}
{% endfor %}



