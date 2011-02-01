# Generated by the protocol buffer compiler.  DO NOT EDIT!

from google.protobuf import descriptor
from google.protobuf import message
from google.protobuf import reflection
from google.protobuf import service
from google.protobuf import service_reflection
from google.protobuf import descriptor_pb2
# @@protoc_insertion_point(imports)


DESCRIPTOR = descriptor.FileDescriptor(
  name='worldstate.proto',
  package='uk.ac.ed.inf.sdp.group4',
  serialized_pb='\n\x10worldstate.proto\x12\x17uk.ac.ed.inf.sdp.group4\"\xdc\x01\n\x0bWorldObject\x12?\n\x08position\x18\x01 \x02(\x0b\x32-.uk.ac.ed.inf.sdp.group4.WorldObject.Position\x12;\n\x06vector\x18\x02 \x01(\x0b\x32+.uk.ac.ed.inf.sdp.group4.WorldObject.Vector\x1a \n\x08Position\x12\t\n\x01x\x18\x01 \x02(\x05\x12\t\n\x01y\x18\x02 \x02(\x05\x1a-\n\x06Vector\x12\x11\n\tdirection\x18\x01 \x02(\x05\x12\x10\n\x08velocity\x18\x02 \x01(\x02\"\xd6\x01\n\x12WorldStateResponse\x12\x0f\n\x07\x63hanged\x18\x01 \x02(\x08\x12\x11\n\ttimestamp\x18\x02 \x02(\x04\x12\x32\n\x04\x62\x61ll\x18\x03 \x01(\x0b\x32$.uk.ac.ed.inf.sdp.group4.WorldObject\x12\x32\n\x04\x62lue\x18\x04 \x01(\x0b\x32$.uk.ac.ed.inf.sdp.group4.WorldObject\x12\x34\n\x06yellow\x18\x05 \x01(\x0b\x32$.uk.ac.ed.inf.sdp.group4.WorldObject\"+\n\x11WorldStateRequest\x12\x16\n\x0elast_timestamp\x18\x01 \x02(\x04\x32}\n\x11WorldStateService\x12h\n\rGetWorldState\x12*.uk.ac.ed.inf.sdp.group4.WorldStateRequest\x1a+.uk.ac.ed.inf.sdp.group4.WorldStateResponseB\x06\x88\x01\x01\x90\x01\x01')




_WORLDOBJECT_POSITION = descriptor.Descriptor(
  name='Position',
  full_name='uk.ac.ed.inf.sdp.group4.WorldObject.Position',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  fields=[
    descriptor.FieldDescriptor(
      name='x', full_name='uk.ac.ed.inf.sdp.group4.WorldObject.Position.x', index=0,
      number=1, type=5, cpp_type=1, label=2,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    descriptor.FieldDescriptor(
      name='y', full_name='uk.ac.ed.inf.sdp.group4.WorldObject.Position.y', index=1,
      number=2, type=5, cpp_type=1, label=2,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  options=None,
  is_extendable=False,
  extension_ranges=[],
  serialized_start=187,
  serialized_end=219,
)

_WORLDOBJECT_VECTOR = descriptor.Descriptor(
  name='Vector',
  full_name='uk.ac.ed.inf.sdp.group4.WorldObject.Vector',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  fields=[
    descriptor.FieldDescriptor(
      name='direction', full_name='uk.ac.ed.inf.sdp.group4.WorldObject.Vector.direction', index=0,
      number=1, type=5, cpp_type=1, label=2,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    descriptor.FieldDescriptor(
      name='velocity', full_name='uk.ac.ed.inf.sdp.group4.WorldObject.Vector.velocity', index=1,
      number=2, type=2, cpp_type=6, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  options=None,
  is_extendable=False,
  extension_ranges=[],
  serialized_start=221,
  serialized_end=266,
)

_WORLDOBJECT = descriptor.Descriptor(
  name='WorldObject',
  full_name='uk.ac.ed.inf.sdp.group4.WorldObject',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  fields=[
    descriptor.FieldDescriptor(
      name='position', full_name='uk.ac.ed.inf.sdp.group4.WorldObject.position', index=0,
      number=1, type=11, cpp_type=10, label=2,
      has_default_value=False, default_value=None,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    descriptor.FieldDescriptor(
      name='vector', full_name='uk.ac.ed.inf.sdp.group4.WorldObject.vector', index=1,
      number=2, type=11, cpp_type=10, label=1,
      has_default_value=False, default_value=None,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
  ],
  extensions=[
  ],
  nested_types=[_WORLDOBJECT_POSITION, _WORLDOBJECT_VECTOR, ],
  enum_types=[
  ],
  options=None,
  is_extendable=False,
  extension_ranges=[],
  serialized_start=46,
  serialized_end=266,
)


_WORLDSTATERESPONSE = descriptor.Descriptor(
  name='WorldStateResponse',
  full_name='uk.ac.ed.inf.sdp.group4.WorldStateResponse',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  fields=[
    descriptor.FieldDescriptor(
      name='changed', full_name='uk.ac.ed.inf.sdp.group4.WorldStateResponse.changed', index=0,
      number=1, type=8, cpp_type=7, label=2,
      has_default_value=False, default_value=False,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    descriptor.FieldDescriptor(
      name='timestamp', full_name='uk.ac.ed.inf.sdp.group4.WorldStateResponse.timestamp', index=1,
      number=2, type=4, cpp_type=4, label=2,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    descriptor.FieldDescriptor(
      name='ball', full_name='uk.ac.ed.inf.sdp.group4.WorldStateResponse.ball', index=2,
      number=3, type=11, cpp_type=10, label=1,
      has_default_value=False, default_value=None,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    descriptor.FieldDescriptor(
      name='blue', full_name='uk.ac.ed.inf.sdp.group4.WorldStateResponse.blue', index=3,
      number=4, type=11, cpp_type=10, label=1,
      has_default_value=False, default_value=None,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    descriptor.FieldDescriptor(
      name='yellow', full_name='uk.ac.ed.inf.sdp.group4.WorldStateResponse.yellow', index=4,
      number=5, type=11, cpp_type=10, label=1,
      has_default_value=False, default_value=None,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  options=None,
  is_extendable=False,
  extension_ranges=[],
  serialized_start=269,
  serialized_end=483,
)


_WORLDSTATEREQUEST = descriptor.Descriptor(
  name='WorldStateRequest',
  full_name='uk.ac.ed.inf.sdp.group4.WorldStateRequest',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  fields=[
    descriptor.FieldDescriptor(
      name='last_timestamp', full_name='uk.ac.ed.inf.sdp.group4.WorldStateRequest.last_timestamp', index=0,
      number=1, type=4, cpp_type=4, label=2,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  options=None,
  is_extendable=False,
  extension_ranges=[],
  serialized_start=485,
  serialized_end=528,
)


_WORLDOBJECT_POSITION.containing_type = _WORLDOBJECT;
_WORLDOBJECT_VECTOR.containing_type = _WORLDOBJECT;
_WORLDOBJECT.fields_by_name['position'].message_type = _WORLDOBJECT_POSITION
_WORLDOBJECT.fields_by_name['vector'].message_type = _WORLDOBJECT_VECTOR
_WORLDSTATERESPONSE.fields_by_name['ball'].message_type = _WORLDOBJECT
_WORLDSTATERESPONSE.fields_by_name['blue'].message_type = _WORLDOBJECT
_WORLDSTATERESPONSE.fields_by_name['yellow'].message_type = _WORLDOBJECT

class WorldObject(message.Message):
  __metaclass__ = reflection.GeneratedProtocolMessageType
  
  class Position(message.Message):
    __metaclass__ = reflection.GeneratedProtocolMessageType
    DESCRIPTOR = _WORLDOBJECT_POSITION
    
    # @@protoc_insertion_point(class_scope:uk.ac.ed.inf.sdp.group4.WorldObject.Position)
  
  class Vector(message.Message):
    __metaclass__ = reflection.GeneratedProtocolMessageType
    DESCRIPTOR = _WORLDOBJECT_VECTOR
    
    # @@protoc_insertion_point(class_scope:uk.ac.ed.inf.sdp.group4.WorldObject.Vector)
  DESCRIPTOR = _WORLDOBJECT
  
  # @@protoc_insertion_point(class_scope:uk.ac.ed.inf.sdp.group4.WorldObject)

class WorldStateResponse(message.Message):
  __metaclass__ = reflection.GeneratedProtocolMessageType
  DESCRIPTOR = _WORLDSTATERESPONSE
  
  # @@protoc_insertion_point(class_scope:uk.ac.ed.inf.sdp.group4.WorldStateResponse)

class WorldStateRequest(message.Message):
  __metaclass__ = reflection.GeneratedProtocolMessageType
  DESCRIPTOR = _WORLDSTATEREQUEST
  
  # @@protoc_insertion_point(class_scope:uk.ac.ed.inf.sdp.group4.WorldStateRequest)


_WORLDSTATESERVICE = descriptor.ServiceDescriptor(
  name='WorldStateService',
  full_name='uk.ac.ed.inf.sdp.group4.WorldStateService',
  file=DESCRIPTOR,
  index=0,
  options=None,
  serialized_start=530,
  serialized_end=655,
  methods=[
  descriptor.MethodDescriptor(
    name='GetWorldState',
    full_name='uk.ac.ed.inf.sdp.group4.WorldStateService.GetWorldState',
    index=0,
    containing_service=None,
    input_type=_WORLDSTATEREQUEST,
    output_type=_WORLDSTATERESPONSE,
    options=None,
  ),
])

class WorldStateService(service.Service):
  __metaclass__ = service_reflection.GeneratedServiceType
  DESCRIPTOR = _WORLDSTATESERVICE
class WorldStateService_Stub(WorldStateService):
  __metaclass__ = service_reflection.GeneratedServiceStubType
  DESCRIPTOR = _WORLDSTATESERVICE

# @@protoc_insertion_point(module_scope)
